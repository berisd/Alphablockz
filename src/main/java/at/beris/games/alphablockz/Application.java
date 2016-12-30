/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz;

import at.beris.games.alphablockz.audio.AudioPlayer;
import at.beris.games.alphablockz.word.Dictionary;
import javazoom.jl.decoder.JavaLayerException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Application extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static final String ID_SOUND_LETTER_MOVING = "letter_moving";
    public static final String ID_SOUND_LETTER_FALLING = "letter_falling";
    public static final String ID_SOUND_WORD_MATCHING = "word_matching";
    public static final String ID_SOUND_LEVEL_COMPLETED = "level_completed";
    public static final String ID_SOUND_GAME_OVER = "game_over";

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

        gameLogic = new GameLogic();

        Dictionary dictionary = Dictionary.getInstance();
        dictionary.load();

        setSize(640, 480);
        setLocationRelativeTo(null);
        setTitle("AlphaBlockz");

        gameScreen = new GameScreen(new CustomGameScreenListener(), gameLogic);
        gameScreen.setEnabled(false);
        gameScreen.setVisible(false);

        titleScreen = new TitleScreen(new CustomTitleScreenListener());
        add(titleScreen);

        InputStream musicStream = this.getClass().getClassLoader().getResourceAsStream("bensound-clearday.mp3");
        InputStream soundStreamLetterMoving = this.getClass().getClassLoader().getResourceAsStream("Eye_Poke-Klocko-584660179.mp3");
        InputStream soundStreamLetterFalling = this.getClass().getClassLoader().getResourceAsStream("Realistic_Punch-Mark_DiAngelo-1609462330.mp3");
        InputStream soundStreamWordMatching = this.getClass().getClassLoader().getResourceAsStream("Gun_Shot-Marvin-1140816320.mp3");
        InputStream soundStreamLevelCompleted = this.getClass().getClassLoader().getResourceAsStream("News_Intro-Maximilien_-1801238420.mp3");
        InputStream soundStreamLGameOver = this.getClass().getClassLoader().getResourceAsStream("Evil_Laugh_2-Sound_Explorer-1081271267.mp3");

        try {
            musicPlayer = new AudioPlayer(new BufferedInputStream(musicStream));
            musicPlayer.setLooping(true);

            actionSoundPlayer = new AudioPlayer();
            actionSoundPlayer.addStream(ID_SOUND_LETTER_MOVING, soundStreamLetterMoving);
            actionSoundPlayer.addStream(ID_SOUND_LETTER_FALLING, soundStreamLetterFalling);

            eventSoundPlayer = new AudioPlayer();
            eventSoundPlayer.addStream(ID_SOUND_WORD_MATCHING, soundStreamWordMatching);
            eventSoundPlayer.addStream(ID_SOUND_LEVEL_COMPLETED, soundStreamLevelCompleted);
            eventSoundPlayer.addStream(ID_SOUND_GAME_OVER, soundStreamLGameOver);
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
            sb.append(System.lineSeparator());
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
            playSound(actionSoundPlayer, ID_SOUND_LETTER_MOVING);
        }

        public void letterFalling() {
            playSound(actionSoundPlayer, ID_SOUND_LETTER_FALLING);
        }

        public void wordMatching() {
            playSound(eventSoundPlayer, ID_SOUND_WORD_MATCHING);
        }

        public void levelStarted() {
            musicPlayer.reset();
            musicPlayer.play();
            actionSoundPlayer.stop();
            eventSoundPlayer.stop();
        }

        public void levelCompleted() {
            musicPlayer.stop();
            playSound(eventSoundPlayer, ID_SOUND_LEVEL_COMPLETED);
        }

        public void gameStarted() {
            musicPlayer.reset();
            musicPlayer.play();
        }

        public void gameOver() {
            musicPlayer.stop();
            playSound(eventSoundPlayer, ID_SOUND_GAME_OVER);
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

    private void playSound(AudioPlayer player, String soundId) {
        if (player.isPlaying()) {
            player.stop();
        }
        player.reset();
        player.setStream(soundId);
        player.play();
    }
}

