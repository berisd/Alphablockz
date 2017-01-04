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

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Word implements Cloneable, Iterable<Letter> {
    public enum Alignment {HORIZONTAL, VERTICAL}

    public enum Direction {LEFT_TO_RIGHT, RIGHT_TO_LEFT, TOP_TO_BOTTOM, BOTTOM_TO_TOP}

    private List<Letter> letters;

    public Word() {
        letters = new ArrayList<Letter>();
    }

    public Word(Word word) {
        this();

        for (Letter letter : word) {
            letters.add((Letter) letter.clone());
        }
    }

    public Word(String word) {
        this();

        for (int i = 0; i < word.length(); i++) {
            letters.add(new Letter(LetterEnum.getByCharacter(word.charAt(i))));
        }
    }

    public void setLetters(List<Letter> letters) {
        this.letters = letters;
    }

    public int length() {
        return letters.size();
    }

    public void add(Letter letter) {
        letters.add(letter);
    }

    public void clear() {
        letters.clear();
    }

    public Letter get(int index) {
        return letters.get(index);
    }


    public void append(Word word) {
        for (Letter letter : word) {
            this.add((Letter) letter.clone());
        }
    }

    public Word reverse() {
        List<Letter> letters = new ArrayList<Letter>();


        for (int index = this.letters.size() - 1; index >= 0; index--) {
            letters.add(this.letters.get(index));
        }

        this.letters = letters;
        return this;
    }

    public boolean isVisible() {
        return true;
    }

    public Object clone() {
        Word clone;
        try {
            clone = (Word) super.clone();
            List<Letter> cloneLetters = new ArrayList<Letter>();

            for (Letter letter : this.letters) {
                cloneLetters.add((Letter) letter.clone());
            }

            clone.setLetters(cloneLetters);
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }

        return clone;
    }

    public void draw(Graphics2D context, int x, int y, int blockWidth, int blockHeight) {
        for (int i = 0; i < letters.size(); i++)
            letters.get(i).draw(context, x + i * Letter.BLOCKS_PER_ROW * blockWidth, y, blockWidth, blockHeight);
    }

    public void draw(Graphics2D context, int x, int y) {
        draw(context, x, y, Block.DEFAULT_BLOCK_WIDTH, Block.DEFAULT_BLOCK_HEIGHT);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Letter letter : letters) {
            sb.append(letter.getCharacter());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return letters != null ? letters.hashCode() : 0;
    }

    public Iterator<Letter> iterator() {
        return this.letters.iterator();
    }
}
