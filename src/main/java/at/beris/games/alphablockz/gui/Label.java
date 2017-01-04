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

import java.awt.Color;
import java.awt.Graphics2D;

public class Label extends Widget {
    private String text;
    private Color color = Color.BLACK;
    private FontEnum font;

    public Label(int x, int y, String text) {
        super(x, y, 0, 0);
        this.text = text;
    }

    public void draw(Graphics2D context) {
        if (!visible)
            return;

        context.setFont(font.getFont());
        context.setColor(color);
        context.drawString(text, x, y);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFont(FontEnum font) {
        this.font = font;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
