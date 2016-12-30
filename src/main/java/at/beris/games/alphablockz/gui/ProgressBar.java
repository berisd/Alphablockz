/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz.gui;

import java.awt.Color;
import java.awt.Graphics2D;

public class ProgressBar implements Drawable {
    int x;
    int y;
    int width = 150;
    int height = 25;
    int percentage = 0;

    public ProgressBar(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public boolean isVisible() {
        return true;
    }

    public void draw(Graphics2D context) {
        int widthPercentage = (int) (((double) width / 100) * percentage);

        context.setColor(Color.BLACK);
        context.drawRect(x, y, width, height);

        if (widthPercentage > 0) {
            context.setColor(Color.BLUE);
            context.fillRect(x + 1, y + 1, widthPercentage - 1, height - 1);
        }
    }
}
