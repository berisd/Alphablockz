/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz;

import at.beris.games.alphablockz.gui.*;
import at.beris.games.alphablockz.gui.Button;
import at.beris.games.alphablockz.gui.Label;
import at.beris.games.alphablockz.gui.Point;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameScreen extends JPanel implements ActionListener {
    private final static Logger LOGGER = Logger.getLogger(GameScreen.class.getName());
    private final static int TIMER_DELAY = 50;
    private final static int MAX_SPEED = 10;
    private final static int INPUT_ENABLER_DELAY_IN_MSEC = 1000;
    private int speed = 5;

    private long startTimeWaitMs = System.currentTimeMillis();

    private Timer timer;
    private GameScreenListener listener;
    private GameLogic gameLogic;
    private Board board;
    private ScheduledExecutorService scheduledExecutorService;
    private InputEnabler inputEnabler;
    private Map<WidgetId, Drawable> widgets;

    private boolean isGamePaused;
    private boolean keyListenerEnabled;
    private boolean mouseListenerEnabled;

    public GameScreen(GameScreenListener listener, GameLogic gameLogic) {
        this.listener = listener;
        this.gameLogic = gameLogic;
        this.board = gameLogic.getBoard();

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        inputEnabler = new InputEnabler();

        setFocusable(true);
        addKeyListener(new CustomKeyListener());
        addMouseListener(new CustomMouseListener());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                requestFocus();
            }
        });

        createWidgets();

        isGamePaused = false;
        keyListenerEnabled = true;
        mouseListenerEnabled = true;
        timer = new Timer(TIMER_DELAY, this);
    }

    private class InputEnabler implements Runnable {
        public void run() {
            keyListenerEnabled = true;
            mouseListenerEnabled = true;
        }
    }

    private void createWidgets() {
        widgets = new LinkedHashMap<WidgetId, Drawable>();
        widgets.put(WidgetId.BOARD, board);
        widgets.put(WidgetId.CURRENT_LETTER, null);

        gameLogic.getWordList().setLocation(new Point(450, 210));
        widgets.put(WidgetId.WORD_LIST, gameLogic.getWordList());

        Label labelTimeLeftCaption = new Label(450, 20, "Time:");
        labelTimeLeftCaption.setFont(FontEnum.STD);
        widgets.put(WidgetId.LABEL_TIME_LEFT_CAPTION, labelTimeLeftCaption);

        Label labelTimeLeft = new Label(450, 20 + 20, "5:00");
        labelTimeLeft.setFont(FontEnum.STD);
        widgets.put(WidgetId.LABEL_TIME_LEFT, labelTimeLeft);

        Label labelLevelCaption = new Label(525, 20, "Level:");
        labelLevelCaption.setFont(FontEnum.STD);
        widgets.put(WidgetId.LABEL_LEVEL_CAPTION, labelLevelCaption);

        Label labelLevel = new Label(525, 20 + 20, Integer.toString(gameLogic.getLevel()));
        labelLevel.setFont(FontEnum.STD);
        widgets.put(WidgetId.LABEL_LEVEL, labelLevel);

        Label labelScoreCaption = new Label(450, 80, "Score: ");
        labelScoreCaption.setFont(FontEnum.STD);
        widgets.put(WidgetId.LABEL_SCORE_CAPTION, labelScoreCaption);

        Label labelScore = new Label(450, 80 + 20, Integer.toString(gameLogic.getScore()));
        labelScore.setFont(FontEnum.STD);
        widgets.put(WidgetId.LABEL_SCORE, labelScore);

        Button buttonPause = new Button(450, 370, 85, 40, "Pause");
        widgets.put(WidgetId.BUTTON_PAUSE, buttonPause);

        Button buttonExit = new Button(540, 370, 85, 40, "Exit");
        widgets.put(WidgetId.BUTTON_EXIT, buttonExit);

        Label labelProgress = new Label(450, 130, "Progress:");
        labelProgress.setFont(FontEnum.STD);
        widgets.put(WidgetId.LABEL_PROGRESS, labelProgress);

        ProgressBar progressBar = new ProgressBar(450, 130 + 10);
        widgets.put(WidgetId.PROGRESS_BAR, progressBar);

        Label labelGameOver = new Label(100, 200, "Game Over");
        labelGameOver.setFont(FontEnum.XLARGE);
        labelGameOver.setVisible(false);
        widgets.put(WidgetId.LABEL_GAME_OVER, labelGameOver);

        Label labelLevelCompleted = new Label(100, 200, "Level completed");
        labelLevelCompleted.setFont(FontEnum.XLARGE);
        labelLevelCompleted.setVisible(false);
        widgets.put(WidgetId.LABEL_LEVEL_COMPLETED, labelLevelCompleted);

        Label labelGamePaused = new Label(100, 200, "Game Paused");
        labelGamePaused.setFont(FontEnum.XLARGE);
        labelGamePaused.setVisible(false);
        widgets.put(WidgetId.LABEL_GAME_PAUSED, labelGamePaused);
    }

    private void resetPlayingScreenWidgets() {
        ((Label) widgets.get(WidgetId.LABEL_SCORE)).setText(Integer.toString(gameLogic.getScore()));
        ((Label) widgets.get(WidgetId.LABEL_LEVEL)).setText(Integer.toString(gameLogic.getLevel()));
        ((ProgressBar) widgets.get(WidgetId.PROGRESS_BAR)).setPercentage(0);

        ((Label) widgets.get(WidgetId.LABEL_GAME_OVER)).setVisible(false);
        ((Label) widgets.get(WidgetId.LABEL_GAME_PAUSED)).setVisible(false);
        ((Label) widgets.get(WidgetId.LABEL_LEVEL_COMPLETED)).setVisible(false);
    }

    protected void drawScreen(Graphics graphics) {
        for (Drawable widget : widgets.values()) {
            if (widget != null && widget.isVisible()) {
                final Graphics2D g2d = (Graphics2D) graphics.create();
                widget.draw(g2d);
                g2d.dispose();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawScreen(g);
    }

    public void actionPerformed(ActionEvent e) {
        boolean executeMainLogic = true;

        long currentTimeInMilliSec = System.currentTimeMillis();
        int playingTimeInSec = gameLogic.getPlayingTimeInSec();

        boolean doWait = (currentTimeInMilliSec - startTimeWaitMs < TIMER_DELAY + (MAX_SPEED - speed + 1) * 125);

        if (playingTimeInSec > GameLogic.LEVEL_MAX_PLAYING_TIME_IN_SEC && !gameLogic.isLevelComplete()) {
            executeMainLogic = false;
            gameOver();
        }

        if (gameLogic.isLevelComplete())
            executeMainLogic = false;

        if (doWait)
            executeMainLogic = false;
        else
            startTimeWaitMs = System.currentTimeMillis();

        if (gameLogic.getLastWordMatch() != null) {
            wordFound();
            if (gameLogic.getNumberOfWordsFound() == gameLogic.wordsRequiredForLevelCompleted()) {
                levelCompleted();
                executeMainLogic = false;
            } else {
                board.setCurrentLetter(gameLogic.createRandomLetter());
            }
        }

        if (executeMainLogic) {
            updatePlayingTimeLabel();
            board.moveLetterDownIfEmptyBelow();

            if (board.collisionDetected(board.getCurrentLetterX(), board.getCurrentLetterY() + 1)) {
                if (board.getCurrentLetterY() <= 0) {
                    gameOver();
                } else {
                    gameLogic.findLongestWordOnBoard();

                    if (gameLogic.wordListContainsWordFound()) {
                        LOGGER.info("Word " + gameLogic.getLastWordMatch().getWord() + " detected");
                        listener.wordMatching();
                    } else {
                        gameLogic.clearLastWordMatch();
                        board.setCurrentLetter(gameLogic.createRandomLetter());
                    }

                    ((Label) widgets.get(WidgetId.LABEL_SCORE)).setText(Integer.toString(gameLogic.getScore()));
                }
            } else {
                board.setCurrentLetterY(board.getCurrentLetterY() + 1);
            }
        }

        repaint();
    }

    private void disableInputForAShortTime() {
        keyListenerEnabled = false;
        mouseListenerEnabled = false;
        scheduledExecutorService.schedule(inputEnabler, INPUT_ENABLER_DELAY_IN_MSEC, TimeUnit.MILLISECONDS);
    }

    private void wordFound() {
        disableInputForAShortTime();
        gameLogic.wordFound();
        gameLogic.addNewWordToWordList();
        ((Label) widgets.get(WidgetId.LABEL_SCORE)).setText(Integer.toString(gameLogic.getScore()));
        ((ProgressBar) widgets.get(WidgetId.PROGRESS_BAR)).setPercentage(gameLogic.getPercentageLevelCompleted());
    }

    private void gameOver() {
        disableInputForAShortTime();
        listener.gameOver();
        timer.stop();
        gameLogic.gameOver();

        ((Label) widgets.get(WidgetId.LABEL_GAME_OVER)).setVisible(true);
    }

    private void levelCompleted() {
        disableInputForAShortTime();
        listener.levelCompleted();
        gameLogic.levelCompleted();

        ((Label) widgets.get(WidgetId.LABEL_LEVEL_COMPLETED)).setVisible(true);
    }

    private void updatePlayingTimeLabel() {
        int timeInSec = GameLogic.LEVEL_MAX_PLAYING_TIME_IN_SEC - gameLogic.getPlayingTimeInSec();

        StringBuilder sb = new StringBuilder(Integer.toString(timeInSec / 60));
        sb.append(':');

        if (timeInSec % 60 < 10)
            sb.append('0');
        sb.append(Integer.toString(timeInSec % 60));

        ((Label) widgets.get(WidgetId.LABEL_TIME_LEFT)).setText(sb.toString());
    }

    private void exitGame() {
        listener.exitGame();
        timer.stop();
        gameLogic.exitGame();
        repaint();
    }

    public void dispose() {
        timer.stop();
    }

    class CustomMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!mouseListenerEnabled)
                return;
            if (gameLogic.isGameOver()) {
                exitGame();
            } else if (gameLogic.isLevelComplete()) {
                startLevel();
            } else if (((Button) widgets.get(WidgetId.BUTTON_EXIT)).contains(e.getX(), e.getY())) {
                exitGame();
            } else if (((Button) widgets.get(WidgetId.BUTTON_PAUSE)).contains(e.getX(), e.getY())) {
                pauseGame();
            }
        }
    }

    public void pauseGame() {
        isGamePaused = !isGamePaused;

        if (isGamePaused) {
            listener.gamePaused();
            timer.stop();
            Label labelGamePaused = new Label(100, 200, "Game Paused");
            labelGamePaused.setFont(FontEnum.XLARGE);
            ((Label) widgets.get(WidgetId.LABEL_GAME_PAUSED)).setVisible(true);
        } else {
            listener.gameStarted();
            ((Label) widgets.get(WidgetId.LABEL_GAME_PAUSED)).setVisible(false);
            timer.start();
        }

        repaint();
    }

    public void startGame() {
        listener.gameStarted();
        gameLogic.startGame();
        startLevel();
    }

    public void startLevel() {
        listener.levelStarted();
        gameLogic.startLevel();
        resetPlayingScreenWidgets();
        board.setCurrentLetter(gameLogic.createRandomLetter());
        repaint();
        timer.start();
    }

    class CustomKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!keyListenerEnabled)
                return;

            LOGGER.info("keyPressed");
            super.keyPressed(e);

            switch (e.getKeyCode()) {
                case KeyEvent.VK_P:
                    pauseGame();
                    break;
                case KeyEvent.VK_E:
                    exitGame();
                    break;
                case KeyEvent.VK_LEFT:
                    LOGGER.info("Key Left");
                    listener.letterMoved();
                    board.moveCurrentLetterToLeft();

                    break;
                case KeyEvent.VK_RIGHT:
                    LOGGER.info("Key Right");
                    listener.letterMoved();
                    board.moveCurrentLetterToRight();
                    break;
                case KeyEvent.VK_UP:
                    LOGGER.info("Key Up");
                    listener.letterMoved();
                    board.getCurrentLetter().setColor(board.getCurrentLetter().getColor().getPreviousForeColor());
                    break;
                case KeyEvent.VK_DOWN:
                    LOGGER.info("Key Down");
                    listener.letterMoved();
                    board.getCurrentLetter().setColor(board.getCurrentLetter().getColor().getNextForeColor());
                    break;
                case KeyEvent.VK_SPACE:
                    LOGGER.info("Key Space");
                    if (gameLogic.isPlaying()) {
                        if (gameLogic.isLevelComplete()) {
                            startLevel();
                        } else if (gameLogic.isGameOver()) {
                            exitGame();
                        } else {
                            listener.letterFalling();
                            board.moveCurrentLetterDown();
                        }
                    }
                    break;
            }
            repaint();
        }
    }
}