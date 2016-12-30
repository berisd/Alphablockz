/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz.gui;

import at.beris.games.alphablockz.FontEnum;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Button extends Widget {
    private String caption;

    public Button(int x, int y, int width, int height, String caption) {
        super(x, y, width, height);
        this.caption = caption;
    }

    public void draw(Graphics2D graphicsContext) {
        graphicsContext.setFont(FontEnum.LARGE.getFont());
        graphicsContext.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(3);

        FontRenderContext fontRenderContext = graphicsContext.getFontRenderContext();
        Rectangle2D stringBounds = FontEnum.LARGE.getFont().getStringBounds(caption, fontRenderContext);

        java.awt.Shape shape = new RoundRectangle2D.Float(x, y, width, height, 8, 8);

        graphicsContext.setStroke(stroke);
        graphicsContext.setColor(Color.black);

        int offsetX = x + (int) ((width - stringBounds.getWidth()) / 2);
        int offsetY = y + (int) ((width - stringBounds.getHeight()) / 2);

        graphicsContext.drawString(caption, offsetX, offsetY);

        graphicsContext.setFont(FontEnum.LARGE_UNDERLINE.getFont());
        graphicsContext.drawString(caption.substring(0, 1), offsetX, offsetY);

        graphicsContext.draw(shape);
    }
}
