/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz.word;

import java.awt.Color;
import java.util.Random;

/**
 * Created by bernd on 15.08.15.
 */
public enum LetterColor {
    WHITE(new Color(255, 255, 255)),    // COLOR OF BOARD
    YELLOW(new Color(204, 204, 102)), // COLOR of EMPTY LETTER BLOCK
    RED(new Color(204, 102, 102)),
    GREEN(new Color(102, 204, 102)),
    BLUE(new Color(102, 102, 204)),
    CYAN(new Color(4, 207, 214));

    private static LetterColor[] colors = values();

    public static int COLOR_TYPE_FOREGROUND = 1;
    public static int COLOR_TYPE_BACKGROUND = 2;

    private Color color;

    LetterColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public int getIndex() {
        return this.ordinal();
    }

    public LetterColor getNext() {
        return colors[(this.ordinal() + 1) % colors.length];
    }

    public LetterColor getNextForeColor() {
        int index = (this.ordinal() + 1) % colors.length;
        if (index == 0)
            index += 2;
        return colors[index];
    }

    public LetterColor getPreviousForeColor() {
        int index = this.ordinal() - 1;

        if (index < 2)
            index += colors.length - 2;

        return colors[index];
    }

    public static LetterColor getRandomForeColor() {
        Random rand = new Random();
        return colors[rand.nextInt(colors.length - 2) + 2];
    }
}
