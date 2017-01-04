/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz.gui;

import at.beris.games.alphablockz.word.Letter;
import at.beris.games.alphablockz.word.LetterColor;
import at.beris.games.alphablockz.word.Word;
import at.beris.games.alphablockz.word.WordMatch;

import java.awt.Graphics2D;

public class Board implements Drawable {
    public static final int DEFAULT_LETTERS_PER_ROW = 14;
    public static final int DEFAULT_LETTERS_PER_COLUMN = 14;


    public static final int DEFAULT_X_NEW_LETTER = DEFAULT_LETTERS_PER_ROW / 2;
    public static final int DEFAULT_Y_NEW_LETTER = -1;

    private int width;
    private int height;

    private Letter[][] letterMatrix;
    private Letter currentLetter;
    private int currentLetterX;
    private int currentLetterY;
    private boolean visible;

    public Board() {
        width = DEFAULT_LETTERS_PER_ROW;
        height = DEFAULT_LETTERS_PER_COLUMN;
        letterMatrix = new Letter[width][height];
        currentLetterX = DEFAULT_X_NEW_LETTER;
        currentLetterY = DEFAULT_Y_NEW_LETTER;
        visible = true;
    }

    public Board(int width, int height) {
        this();
        this.width = width;
        this.height = height;
        letterMatrix = new Letter[width][height];
    }

    public Letter getCurrentLetter() {
        return currentLetter;
    }

    public void setCurrentLetter(Letter currentLetter) {
        setCurrentLetter(currentLetter, DEFAULT_X_NEW_LETTER, DEFAULT_Y_NEW_LETTER);
    }

    public void setCurrentLetter(Letter currentLetter, int x, int y) {
        this.currentLetter = currentLetter;
        this.currentLetterX = x;
        this.currentLetterY = y;
        letterMatrix[currentLetterY()][x] = currentLetter;
    }

    private int currentLetterY() {
        return currentLetterY < 0 ? 0 : this.currentLetterY;
    }

    public void moveCurrentLetterToLeft() {
        if (!collisionDetected(currentLetterX - 1, currentLetterY())) {
            letterMatrix[currentLetterY()][this.currentLetterX] = null;
            this.currentLetterX--;
            letterMatrix[currentLetterY()][this.currentLetterX] = currentLetter;
        }
    }

    public void moveCurrentLetterToRight() {
        if (!collisionDetected(currentLetterX + 1, currentLetterY())) {
            letterMatrix[currentLetterY()][this.currentLetterX] = null;
            this.currentLetterX++;
            letterMatrix[currentLetterY()][this.currentLetterX] = currentLetter;
        }
    }

    public void moveCurrentLetterDown() {
        if (!collisionDetected(currentLetterX, currentLetterY() + 1)) {
            letterMatrix[currentLetterY()][this.currentLetterX] = null;
            this.currentLetterY++;
            letterMatrix[currentLetterY()][this.currentLetterX] = currentLetter;
        }
    }

    public int getCurrentLetterX() {
        return currentLetterX;
    }

    public void setCurrentLetterX(int currentLetterX) {
        letterMatrix[currentLetterY()][this.currentLetterX] = null;
        this.currentLetterX = currentLetterX;
        letterMatrix[currentLetterY()][this.currentLetterX] = currentLetter;
    }

    public int getCurrentLetterY() {
        return currentLetterY;
    }

    public void setCurrentLetterY(int currentLetterY) {
        letterMatrix[currentLetterY()][this.currentLetterX] = null;
        this.currentLetterY = currentLetterY;
        letterMatrix[currentLetterY()][this.currentLetterX] = currentLetter;
    }

    public int getWidth() {
        return width;
    }

    public boolean collisionDetected(int newX, int newY) {
        boolean collisionDetected = false;

        if ((newX < 0) || (newY < 0)) {
            collisionDetected = true;
        } else if (newY >= height) {
            collisionDetected = true;
        } else if (newX >= width) {
            collisionDetected = true;
        } else if (letterMatrix[newY][newX] != null && currentLetterY >= 0) {
            collisionDetected = true;
        }

        return collisionDetected;
    }

    public Letter getLetter(int x, int y) {
        return letterMatrix[y][x];
    }


    public void removeWord(WordMatch wordMatch) {
        Word word = wordMatch.getWord();
        Point location = wordMatch.getLocation();
        Word.Direction direction = wordMatch.getDirection();
        Word.Alignment alignment = wordMatch.getAlignment();

        int colIndex = location.getX();
        int rowIndex = location.getY();
        for (int letterIndex = 0; letterIndex < word.length(); letterIndex++) {
            letterMatrix[rowIndex][colIndex] = null;
            if (alignment == Word.Alignment.HORIZONTAL) {
                if (direction == Word.Direction.LEFT_TO_RIGHT)
                    colIndex++;
                else
                    colIndex--;
            } else {
                if (direction == Word.Direction.TOP_TO_BOTTOM)
                    rowIndex++;
                else
                    rowIndex--;
            }
        }
    }

    public void moveLetterDownIfEmptyBelow() {
        for (int colIndex = 0; colIndex < width; colIndex++) {
            for (int rowIndex = height - 1; rowIndex > 0; rowIndex--) {
                if (colIndex == currentLetterX && rowIndex == currentLetterY())
                    continue;
                if (rowIndex - 1 == currentLetterY())
                    continue;

                if (letterMatrix[rowIndex][colIndex] == null && letterMatrix[rowIndex - 1][colIndex] != null) {
                    letterMatrix[rowIndex][colIndex] = letterMatrix[rowIndex - 1][colIndex];
                    letterMatrix[rowIndex - 1][colIndex] = null;
                }
            }
        }
    }

    public void clear() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                letterMatrix[y][x] = null;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int colIndex = 0; colIndex < width; colIndex++) {

                Letter letter = letterMatrix[rowIndex][colIndex];

                if (letter == null) {
                    Block.drawBlock(graphics2D, 0 + colIndex * Letter.BLOCKS_PER_ROW * Block.DEFAULT_BLOCK_WIDTH, 0 + rowIndex * Letter.BLOCKS_PER_COLUMN * Block.DEFAULT_BLOCK_HEIGHT, LetterColor.WHITE.getColor(), Block.DEFAULT_BLOCK_WIDTH * Letter.BLOCKS_PER_ROW, Block.DEFAULT_BLOCK_HEIGHT * Letter.BLOCKS_PER_COLUMN);
                } else {
                    letter.draw(graphics2D, 0 + colIndex * Letter.BLOCKS_PER_ROW * Block.DEFAULT_BLOCK_WIDTH, 0 + rowIndex * Letter.BLOCKS_PER_COLUMN * Block.DEFAULT_BLOCK_HEIGHT);
                }
            }
        }
    }

    public Letter[][] getLetterMatrix() {
        return letterMatrix;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Board{" +
                "width=" + width +
                ", height=" + height +
                "}" + System.lineSeparator());

        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int colIndex = 0; colIndex < width; colIndex++) {
                sb.append(letterMatrix[rowIndex][colIndex] != null ? letterMatrix[rowIndex][colIndex].getCharacter() : ".");
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
