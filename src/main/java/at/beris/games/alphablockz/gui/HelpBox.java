/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz.gui;

import at.beris.games.alphablockz.FontEnum;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

public class HelpBox extends Widget {

    public HelpBox(int x, int y, int width, int height, boolean visible) {
        super(x, y, width, height);
        this.visible = visible;
    }

    public void draw(Graphics2D graphicsContext) {
        if (!visible)
            return;

        Stroke stroke = new BasicStroke(3);

        java.awt.Shape shape = new RoundRectangle2D.Float(x, y, width, height, 8, 8);

        graphicsContext.setStroke(stroke);
        graphicsContext.setColor(Color.black);
        graphicsContext.draw(shape);

        graphicsContext.setColor(Color.WHITE);
        graphicsContext.fill(shape);

        graphicsContext.setColor(Color.black);

        int offsetX = x + 10;
        int offsetY = y;
        graphicsContext.setFont(FontEnum.SMALL_BOLD.getFont());
        offsetY += 25;
        graphicsContext.drawString("Help:", offsetX, offsetY);
        graphicsContext.setFont(FontEnum.SMALL.getFont());
        offsetY += 25;
        graphicsContext.drawString("A selection of words is randomly chosen in different colors", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("and displayed in a list to the right.", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("Letters of those words are falling down in random colors in a grid.", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("Arrange the letters to form a word from the list in the same color", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("horizontally or vertically and straight or reversed.", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("If you have done all the words in a given time", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("you have completed the level.", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("A progess bar on the right shows you the ratio of the number of words", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("found to the number of words required to complete the level.", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("For every word found and second left after completing a level", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("you will be awarded score points.", offsetX, offsetY);
        offsetY += 30;
        graphicsContext.drawString("The CURSOR LEFT and RIGHT keys MOVE the falling letter", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("to the left and right.", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("The CURSOR UP and DOWN keys CHANGE THE COLOR of the letter.", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("The SPACE key increases the SPEED of the falling letter.", offsetX, offsetY);
        offsetY += 20;
        graphicsContext.drawString("The P key will PAUSE or continue a game and the e key EXIT the game.", offsetX, offsetY);
    }
}
