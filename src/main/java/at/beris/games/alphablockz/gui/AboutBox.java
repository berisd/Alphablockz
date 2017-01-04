/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz.gui;

import at.beris.games.alphablockz.Application;
import at.beris.games.alphablockz.FontEnum;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import static at.beris.games.alphablockz.Application.ID_PROPERTY_BUILD_TIMESTAMP;
import static at.beris.games.alphablockz.Application.ID_PROPERTY_VERSION;

public class AboutBox extends Widget {

    public AboutBox(int x, int y, int width, int height, boolean visible) {
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

        graphicsContext.setStroke(new BasicStroke(1));

        int offsetY = y;
        offsetY += 25;
        drawCenteredText(graphicsContext, FontEnum.SMALL_BOLD.getFont(), offsetY, "Alphablockz");
        offsetY += 20;
        drawCenteredText(graphicsContext, FontEnum.SMALL_BOLD.getFont(), offsetY, "v" + Application.getProperty(ID_PROPERTY_VERSION));
        offsetY += 20;
        drawCenteredText(graphicsContext, FontEnum.XSMALL.getFont(), offsetY, "(Build timestamp: " + Application.getProperty(ID_PROPERTY_BUILD_TIMESTAMP));
        offsetY += 10;
        graphicsContext.drawLine(x + 5, offsetY, x + width - 2 * 5, offsetY);
        offsetY += 20;
        drawCenteredText(graphicsContext, FontEnum.XSMALL.getFont(), offsetY, "Author: Bernd Riedl <bernd.riedl@gmail.com>");
        offsetY += 20;
        drawCenteredText(graphicsContext, FontEnum.XSMALL.getFont(), offsetY, "Homepage: http://www.beris.at");
        offsetY += 10;
        graphicsContext.drawLine(x + 5, offsetY, x + width - 2 * 5, offsetY);
        offsetY += 20;
        drawCenteredText(graphicsContext, FontEnum.XSMALL.getFont(), offsetY, "This Software is freeware.");
        offsetY += 20;
        drawCenteredText(graphicsContext, FontEnum.XSMALL.getFont(), offsetY, "(see LICENSE.txt for details)");
        offsetY += 20;
        drawCenteredText(graphicsContext, FontEnum.XSMALL.getFont(), offsetY, "Uses 3rd party libraries and resources.");
        offsetY += 20;
        drawCenteredText(graphicsContext, FontEnum.XSMALL.getFont(), offsetY, "(see NOTICE.txt for details)");
    }

    private void drawCenteredText(Graphics2D graphicsContext, Font font, int y, String text) {
        graphicsContext.setFont(font);
        FontRenderContext fontRenderContext = graphicsContext.getFontRenderContext();
        Rectangle2D stringBounds = font.getStringBounds(text, fontRenderContext);

        int x = this.x + (int) ((width - stringBounds.getWidth()) / 2);

        graphicsContext.drawString(text, x, y);
    }
}
