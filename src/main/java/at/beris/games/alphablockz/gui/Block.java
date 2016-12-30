/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz.gui;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Block {
    public static final int DEFAULT_BLOCK_WIDTH = 6;
    public static final int DEFAULT_BLOCK_HEIGHT = 6;

    public static void drawBlock(Graphics2D graphics2D, int x, int y, Color color, int blockWidth, int blockHeight) {
        graphics2D.setColor(color);
        graphics2D.fillRect(x + 1, y + 1, blockWidth - 2, blockHeight - 2);

        graphics2D.setColor(color.brighter());
        graphics2D.drawLine(x, y, x, y + blockHeight - 1);
        graphics2D.drawLine(x, y, x + blockWidth - 1, y);

        graphics2D.setColor(color.darker());
        graphics2D.drawLine(x + 1, y + blockHeight - 1,
                x + blockWidth - 1, y + blockHeight - 1);
        graphics2D.drawLine(x + blockWidth - 1, y + 1,
                x + blockWidth - 1, y + blockHeight - 1);
    }
}
