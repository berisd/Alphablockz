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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WordList implements Drawable {
    private Point location;
    private final LinkedList<Word> wordList;

    private class Word {
        public final Color color;
        public final String string;

        public Word(String string, Color color) {
            this.string = string;
            this.color = color;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    public WordList() {
        location = new Point(0, 0);
        wordList = new LinkedList<Word>();
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getRandomWord() {
        if (wordList.size() == 0)
            return null;

        Random rand = new Random();
        int randomIndex = rand.nextInt(wordList.size());

        return wordList.get(randomIndex).string;
    }

    public boolean isVisible() {
        return true;
    }

    public void draw(Graphics2D context) {
        if (wordList.size() == 0)
            return;

        context.setFont(FontEnum.LARGE.getFont());

        for (int i = 0; i < wordList.size(); i++) {
            Word word = this.wordList.get(i);
            context.setColor(word.color);
            context.drawString(word.string, location.getX(), location.getY() + i * 30);
        }
    }

    public boolean remove(String word) {
        boolean removed = false;
        for (Word currentWord : this.wordList) {
            if (currentWord.string.equals(word)) {
                removed = wordList.remove(currentWord);
                break;
            }
        }
        return removed;
    }

    public boolean contains(String word) {
        boolean containsWord = false;

        for (Word currentWord : this.wordList) {
            if (currentWord.string.equals(word)) {
                containsWord = true;
                break;
            }
        }
        return containsWord;
    }

    public int size() {
        return wordList.size();
    }

    public void add(String word, Color color) {
        wordList.add(new Word(word, color));
    }

    public void push(String word, Color color) {
        wordList.push(new Word(word, color));
    }

    public void clear() {
        wordList.clear();
    }

    public List<String> asStringList() {
        List<String> stringList = new ArrayList<String>();
        for (Word word : wordList) {
            stringList.add(word.toString());
        }
        return stringList;
    }
}
