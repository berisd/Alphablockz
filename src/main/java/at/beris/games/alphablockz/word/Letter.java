/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz.word;

import at.beris.games.alphablockz.gui.Block;

import java.awt.Color;
import java.awt.Graphics2D;

public class Letter implements Cloneable {

    public static final int BLOCKS_PER_ROW = 5;
    public static final int BLOCKS_PER_COLUMN = 5;

    protected LetterColor color;
    protected LetterColor backgroundColor;
    protected boolean isFlashing;
    protected LetterEnum letterEnum;

    public Letter(LetterEnum letterEnum) {
        this.letterEnum = letterEnum;
        this.backgroundColor = LetterColor.WHITE;
        this.color = LetterColor.RED;
        this.isFlashing = false;
    }

    public Letter(char character) {
        this(LetterEnum.getByCharacter(character));
    }


    public LetterColor getColor() {
        return color;
    }

    public void setColor(LetterColor color) {
        this.color = color;
    }

    public void setBackgroundColor(LetterColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getWidth() {
        return letterEnum.getPixelData()[0].length;
    }

    public int getHeight() {
        return letterEnum.getPixelData().length;
    }

    public int getPixel(int x, int y) {
        return letterEnum.getPixelData()[y][x];
    }

    public char getCharacter() {
        return letterEnum.getCharacter();
    }

    public boolean isVisible() {
        return true;
    }

    public void draw(Graphics2D graphics2D, int x, int y) {
        draw(graphics2D, x, y, Block.DEFAULT_BLOCK_WIDTH, Block.DEFAULT_BLOCK_HEIGHT);
    }

    public void draw(Graphics2D graphics2D, int x, int y, int blockWidth, int blockHeight) {
        Color blockColor;

        for (int rowIndex = 0; rowIndex < getHeight(); rowIndex++) {
            for (int colIndex = 0; colIndex < getWidth(); colIndex++) {
                if (getPixel(colIndex, rowIndex) == LetterColor.COLOR_TYPE_BACKGROUND)
                    blockColor = backgroundColor.getColor();
                else
                    blockColor = color.getColor();

                Block.drawBlock(graphics2D, x + blockWidth * colIndex, y + blockHeight * rowIndex, blockColor, blockWidth, blockHeight);
            }
        }
    }


    public Object clone() {
        Letter clone;
        try {
            clone = (Letter) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        return clone;
    }

    @Override
    public String toString() {
        return String.valueOf(letterEnum.getCharacter());
    }
}
