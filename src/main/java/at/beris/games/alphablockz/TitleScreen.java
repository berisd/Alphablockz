/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz;

import at.beris.games.alphablockz.gui.*;
import at.beris.games.alphablockz.gui.Button;
import at.beris.games.alphablockz.gui.Label;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class TitleScreen extends JPanel implements ActionListener {
    private final static Logger LOGGER = Logger.getLogger(TitleScreen.class.getName());
    private final static int TIMER_DELAY = 500;

    private TitleScreenListener listener;
    private Map<WidgetId, Drawable> widgets;
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
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawScreen(g);
    }

    private void createWidgets() {
        widgets = new LinkedHashMap<WidgetId, Drawable>();
        widgets.put(WidgetId.LOGO_ALPHA, new Logo(132, 40, "alpha"));
        widgets.put(WidgetId.LOGO_BLOCKZ, new Logo(82, 115, "blockz"));

        int x = 85;
        int y = 285;
        Button buttonStart = new Button(x, y, 100, 50, "Start");
        widgets.put(WidgetId.BUTTON_START, buttonStart);
        Button buttonHelp = new Button(x + 115, y, 100, 50, "Help");
        widgets.put(WidgetId.BUTTON_HELP, buttonHelp);
        Button buttonAbout = new Button(x + 2 * 115, y, 100, 50, "About");
        widgets.put(WidgetId.BUTTON_ABOUT, buttonAbout);
        Button buttonQuit = new Button(x + 3 * 115, y, 100, 50, "Quit");
        widgets.put(WidgetId.BUTTON_QUIT, buttonQuit);

        Label labelHighScore = new Label(230, 240, "Highscore: " + listener.getHighScore());
        labelHighScore.setFont(FontEnum.LARGE);
        labelHighScore.setColor(Color.BLUE);
        widgets.put(WidgetId.LABEL_HIGHSCORE, labelHighScore);

        Label labelAuthor = new Label(180, 395, "(C) 2015 Bernd Riedl");
        labelAuthor.setFont(FontEnum.LARGE);
        widgets.put(WidgetId.LABEL_AUTHOR, labelAuthor);

        widgets.put(WidgetId.ABOUT_BOX, new AboutBox(185, 100, 280, 225, false));
        widgets.put(WidgetId.HELP_BOX, new HelpBox(65, 35, 500, 380, false));
    }

    protected void drawScreen(Graphics graphics) {
        for (Drawable widget : widgets.values()) {
            final Graphics2D g2d = (Graphics2D) graphics.create();
            widget.draw(g2d);
            g2d.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        ((Logo) widgets.get(WidgetId.LOGO_ALPHA)).cycleLetterColors(true);
        ((Logo) widgets.get(WidgetId.LOGO_BLOCKZ)).cycleLetterColors(false);
        ((Label) widgets.get(WidgetId.LABEL_HIGHSCORE)).setText("Highscore: " + listener.getHighScore());
        repaint();
    }

    class CustomMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            AboutBox aboutBox = (AboutBox) widgets.get(WidgetId.ABOUT_BOX);
            HelpBox helpBox = (HelpBox) widgets.get(WidgetId.HELP_BOX);
            Button buttonStart = (Button) widgets.get(WidgetId.BUTTON_START);
            Button buttonHelp = (Button) widgets.get(WidgetId.BUTTON_HELP);
            Button buttonAbout = (Button) widgets.get(WidgetId.BUTTON_ABOUT);
            Button buttonQuit = (Button) widgets.get(WidgetId.BUTTON_QUIT);

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

            AboutBox aboutBox = (AboutBox) widgets.get(WidgetId.ABOUT_BOX);
            HelpBox helpBox = (HelpBox) widgets.get(WidgetId.HELP_BOX);

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
