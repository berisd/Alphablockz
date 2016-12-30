/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz;

import at.beris.games.alphablockz.gui.*;
import at.beris.games.alphablockz.word.Letter;
import at.beris.games.alphablockz.word.LetterColor;
import at.beris.games.alphablockz.word.Word;
import org.apache.log4j.Logger;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class TitleScreen extends JPanel implements ActionListener {
    private final static Logger LOGGER = Logger.getLogger(TitleScreen.class.getName());

    private final static int TIMER_DELAY = 500;

    public static final String ID_BUTTON_START = "buttonStart";
    public static final String ID_BUTTON_HELP = "buttonHelp";
    public static final String ID_BUTTON_ABOUT = "buttonAbout";
    public static final String ID_BUTTON_QUIT = "buttonQuit";
    public static final String ID_ABOUT_BOX = "aboutBox";
    public static final String ID_HELP_BOX = "helpBox";
    public static final String ID_LABEL_HIGHSCORE = "labelHighscore";
    public static final String ID_LABEL_AUTHOR = "labelAuthor";

    private TitleScreenListener listener;
    private Map<String, Drawable> widgets;
    private Word[] titleWords;

    private Timer timer;

    public TitleScreen(TitleScreenListener listener) {
        this.listener = listener;
        setFocusable(true);
        addKeyListener(new CustomKeyListener());
        addMouseListener(new CustomMouseListener());

        timer = new Timer(TIMER_DELAY, this);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                requestFocus();
                timer.start();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                timer.stop();
            }
        });

        createWidgets();
        createWords();

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTitleScreen(g);
    }

    private void createWidgets() {
        widgets = new LinkedHashMap<String, Drawable>();
        int x = 85;
        int y = 285;
        Button buttonStart = new Button(x, y, 100, 50, "Start");
        widgets.put(ID_BUTTON_START, buttonStart);
        Button buttonHelp = new Button(x + 115, y, 100, 50, "Help");
        widgets.put(ID_BUTTON_HELP, buttonHelp);
        Button buttonAbout = new Button(x + 2 * 115, y, 100, 50, "About");
        widgets.put(ID_BUTTON_ABOUT, buttonAbout);
        Button buttonQuit = new Button(x + 3 * 115, y, 100, 50, "Quit");
        widgets.put(ID_BUTTON_QUIT, buttonQuit);

        Label labelHighScore = new Label(230, 240, "Highscore: " + listener.getHighScore());
        labelHighScore.setFont(FontEnum.LARGE);
        labelHighScore.setColor(Color.BLUE);
        widgets.put(ID_LABEL_HIGHSCORE, labelHighScore);

        Label labelAuthor = new Label(180, 395, "(C) 2015 Bernd Riedl");
        labelAuthor.setFont(FontEnum.LARGE);
        widgets.put(ID_LABEL_AUTHOR, labelAuthor);

        widgets.put(ID_ABOUT_BOX, new AboutBox(185, 100, 280, 225, false));
        widgets.put(ID_HELP_BOX, new HelpBox(65, 35, 500, 380, false));
    }

    private void createWords() {
        LetterColor[] foregroundColors = {LetterColor.RED, LetterColor.GREEN, LetterColor.BLUE, LetterColor.CYAN};
        titleWords = new Word[2];
        int y = 0;

        for (Word word : new Word[]{new Word("alpha"), new Word("blockz")}) {
            for (int x = 0; x < word.length(); x++) {
                Letter letter = word.get(x);
                letter.setColor(foregroundColors[x % foregroundColors.length]);
                letter.setBackgroundColor(LetterColor.YELLOW);
            }
            titleWords[y] = word;
            y += 1;
        }
    }

    protected void drawTitleScreen(Graphics graphics) {
        final Graphics2D graphics2D = (Graphics2D) graphics.create();
        int x = 132;
        int y = 40;
        for (Word word : titleWords) {
            word.draw(graphics2D, x, y, 15, 15);
            x -= 50;
            y += 5 * 15;
        }

        for (Object o : widgets.entrySet()) {
            final Graphics2D g2d = (Graphics2D) graphics.create();
            Map.Entry pair = (Map.Entry) o;
            Drawable widget = (Drawable) pair.getValue();
            widget.draw(g2d);
            g2d.dispose();
        }

        graphics2D.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        boolean reverseColors = true;
        for (Word word : titleWords) {
            for (Letter letter : word) {
                letter.setColor(reverseColors ? letter.getColor().getPreviousForeColor() : letter.getColor().getNextForeColor());
            }
            reverseColors = false;
        }

        ((Label) widgets.get(ID_LABEL_HIGHSCORE)).setText("Highscore: " + listener.getHighScore());

        repaint();
    }

    class CustomMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            AboutBox aboutBox = (AboutBox) widgets.get(ID_ABOUT_BOX);
            HelpBox helpBox = (HelpBox) widgets.get(ID_HELP_BOX);
            Button buttonStart = (Button) widgets.get(ID_BUTTON_START);
            Button buttonHelp = (Button) widgets.get(ID_BUTTON_HELP);
            Button buttonAbout = (Button) widgets.get(ID_BUTTON_ABOUT);
            Button buttonQuit = (Button) widgets.get(ID_BUTTON_QUIT);

            if (aboutBox.isVisible() && aboutBox.contains(e.getX(), e.getY())) {
                aboutBox.setVisible(false);
                repaint();
            } else if (helpBox.isVisible() && helpBox.contains(e.getX(), e.getY())) {
                helpBox.setVisible(false);
                repaint();
            } else if (buttonStart.contains(e.getX(), e.getY())) {
                startGame();
                listener.startGame();
            } else if (!helpBox.isVisible() && buttonHelp.contains(e.getX(), e.getY())) {
                helpBox.setVisible(true);
                repaint();
            } else if (!aboutBox.isVisible() && buttonAbout.contains(e.getX(), e.getY())) {
                aboutBox.setVisible(true);
                repaint();
            } else if (buttonQuit.contains(e.getX(), e.getY())) {
                listener.quitGame();
            } else if (aboutBox.isVisible() && aboutBox.contains(e.getX(), e.getY())) {
                aboutBox.setVisible(false);
                repaint();
            }
        }
    }

    class CustomKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            AboutBox aboutBox = (AboutBox) widgets.get(ID_ABOUT_BOX);
            HelpBox helpBox = (HelpBox) widgets.get(ID_HELP_BOX);

            switch (e.getKeyCode()) {
                case KeyEvent.VK_S:
                    LOGGER.info("Start game");
                    startGame();
                    listener.startGame();
                    break;
                case KeyEvent.VK_Q:
                    LOGGER.info("Quit game");
                    listener.quitGame();
                    break;
                case KeyEvent.VK_A:
                    aboutBox.setVisible(!aboutBox.isVisible());
                    break;
                case KeyEvent.VK_H:
                    helpBox.setVisible(!helpBox.isVisible());
                    break;
            }

            repaint();
        }
    }

    public void dispose() {
        timer.stop();
    }

    public void startGame() {
        timer.stop();
    }
}
