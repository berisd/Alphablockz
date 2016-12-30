/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz.word;

import at.beris.games.alphablockz.gui.WordList;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.*;

public class WordListTest {
    WordList wordList;

    @Before
    public void setup() {
        wordList = new WordList();
        wordList.add("teapot", Color.BLACK);
        wordList.add("cheesecake", Color.BLACK);
        wordList.add("grandmother", Color.BLACK);
        wordList.add("village", Color.BLACK);
        wordList.add("factory", Color.BLACK);
    }

    @Test
    public void getRandomWord() {
        String firstWord = wordList.getRandomWord();
        String secondWord = wordList.getRandomWord();

        while (firstWord.equals(secondWord)) {
            firstWord = wordList.getRandomWord();
            secondWord = wordList.getRandomWord();
        }
        assertTrue(firstWord.length() > 0);
        assertTrue(secondWord.length() > 0);
        assertNotEquals(firstWord.toString(), secondWord.toString());
    }

    @Test
    public void containsWord() {
        assertTrue(wordList.contains("village"));
    }

    @Test
    public void RemoveWord() {
        String word = "village";

        assertEquals(5, wordList.size());
        wordList.remove(word);
        assertEquals(4, wordList.size());
        assertFalse(wordList.contains(word));
    }
}
