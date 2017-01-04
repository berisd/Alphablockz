/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz;

import at.beris.games.alphablockz.audio.AudioPlayer;
import at.beris.games.alphablockz.audio.AudioResource;
import at.beris.games.alphablockz.audio.AudioResourceData;
import at.beris.games.alphablockz.word.Dictionary;
import javazoom.jl.decoder.JavaLayerException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.util.Properties;

public class Application extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static final String ID_PROPERTY_VERSION = "version";
    public static final String ID_PROPERTY_BUILD_TIMESTAMP = "build.timestamp";
    public static final String ID_PROPERTY_LOG_LEVEL = "log.level";

    private static final Properties properties = new Properties();

    private GameScreen gameScreen;
    private TitleScreen titleScreen;

    private AudioPlayer musicPlayer;
    private AudioPlayer actionSoundPlayer;
    private AudioPlayer eventSoundPlayer;

    private GameLogic gameLogic;

    public Application() {
        loadProperties();
        initLogging();
        initGame();
        initVideo();
        initAudio();
    }

    private void initGame() {
        gameLogic = new GameLogic();
        Dictionary dictionary = Dictionary.getInstance();
        dictionary.load();
    }

    private void initVideo() {
        setSize(640, 480);
        setLocationRelativeTo(null);
        setTitle("AlphaBlockz");

        gameScreen = new GameScreen(new CustomGameScreenListener(), gameLogic);
        gameScreen.setEnabled(false);
        gameScreen.setVisible(false);

        titleScreen = new TitleScreen(new CustomTitleScreenListener());
        add(titleScreen);
    }

    private void initAudio() {
        try {
            musicPlayer = new AudioPlayer();
            musicPlayer.addAudioResource(new AudioResource(AudioResourceData.GAME_MUSIC));
            musicPlayer.setResource(AudioResourceData.GAME_MUSIC);
            musicPlayer.setLooping(true);

            actionSoundPlayer = new AudioPlayer();
            actionSoundPlayer.addAudioResource(new AudioResource(AudioResourceData.SOUND_LETTER_MOVING));
            actionSoundPlayer.addAudioResource(new AudioResource(AudioResourceData.SOUND_LETTER_FALLING));

            eventSoundPlayer = new AudioPlayer();
            eventSoundPlayer.addAudioResource(new AudioResource(AudioResourceData.SOUND_WORD_MATCHING));
            eventSoundPlayer.addAudioResource(new AudioResource(AudioResourceData.SOUND_LEVEL_COMPLETED));
            eventSoundPlayer.addAudioResource(new AudioResource(AudioResourceData.SOUND_GAME_OVER));
        } catch (JavaLayerException e) {
            logException(e);
        }
    }

    private void loadProperties() {
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (Exception e) {
            LOGGER.warn("Properties file not found");
            Application.logException(e);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                final Application app = new Application();

                app.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                app.setResizable(false);
                app.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        app.quit();
                    }
                });

                app.setVisible(true);
            }
        });
    }

    public void initLogging() {
        Logger rootLogger = Logger.getRootLogger();
        String property = properties.getProperty(ID_PROPERTY_LOG_LEVEL);

        if (property != null)
            rootLogger.setLevel(Level.toLevel(property));
    }

    public void quit() {
        if (JOptionPane.showConfirmDialog(null,
                "Are you sure to quit this game?", "Quit game?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

            musicPlayer.terminate();
            actionSoundPlayer.terminate();
            eventSoundPlayer.terminate();
            gameScreen.dispose();
            titleScreen.dispose();
            System.exit(0);
        }
    }

    private void showScreen(JPanel screen) {
        add(screen);
        screen.setEnabled(true);
        screen.setVisible(true);
    }

    private void hideScreen(JPanel screen) {
        remove(screen);
        screen.setVisible(false);
        screen.setEnabled(false);
    }

    public static void logException(Throwable throwable) {
        StringBuilder sb = new StringBuilder(throwable.getClass().getName());

        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(System.getProperty("line.separator"));
            sb.append(element.toString());
        }

        LOGGER.warn(sb.toString());
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    private class CustomTitleScreenListener implements TitleScreenListener {
        public void startGame() {
            LOGGER.info("Start game");
            hideScreen(titleScreen);
            showScreen(gameScreen);
            gameScreen.startGame();
        }

        public void quitGame() {
            quit();
        }

        public int getHighScore() {
            return gameLogic.getHighScore();
        }
    }

    private class CustomGameScreenListener implements GameScreenListener {
        public void letterMoved() {
            playSound(actionSoundPlayer, AudioResourceData.SOUND_LETTER_MOVING);
        }

        public void letterFalling() {
            playSound(actionSoundPlayer, AudioResourceData.SOUND_LETTER_FALLING);
        }

        public void wordMatching() {
            playSound(eventSoundPlayer, AudioResourceData.SOUND_WORD_MATCHING);
        }

        public void levelStarted() {
            musicPlayer.reset();
            musicPlayer.play();
            actionSoundPlayer.stop();
            eventSoundPlayer.stop();
        }

        public void levelCompleted() {
            musicPlayer.stop();
            playSound(eventSoundPlayer, AudioResourceData.SOUND_LEVEL_COMPLETED);
        }

        public void gameStarted() {
            musicPlayer.reset();
            musicPlayer.play();
        }

        public void gameOver() {
            musicPlayer.stop();
            playSound(eventSoundPlayer, AudioResourceData.SOUND_GAME_OVER);
        }

        public void exitGame() {
            LOGGER.info("Exit game");
            musicPlayer.stop();
            actionSoundPlayer.stop();
            eventSoundPlayer.stop();
            hideScreen(gameScreen);
            showScreen(titleScreen);
        }

        public void gamePaused() {
            musicPlayer.stop();
        }
    }

    private void playSound(AudioPlayer player, AudioResourceData audioResourceData) {
        if (player.isPlaying()) {
            player.stop();
        }
        player.reset();
        player.setResource(audioResourceData);
        player.play();
    }
}

