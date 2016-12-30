/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz.word;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordTest {
    Word word;
    Dictionary dictionary;

    @Before
    public void setup() {
        word = new Word("test");

        dictionary = Dictionary.getInstance();
        dictionary.clear();
        dictionary.add("cheesecake");
        dictionary.add("housewife");
        dictionary.add("mister");
        dictionary.add("testrun");
    }

    @Test
    public void createWordFromString() {
        Word wordAlpha = new Word("alpha");
        assertEquals("alpha", wordAlpha.toString());
    }

    @Test
    public void appendWord() {
        Word wordAlpha = new Word("alpha");
        wordAlpha.append(new Word("beta"));
        assertEquals("alphabeta", wordAlpha.toString());
    }

    @Test
    public void equalsWord() {
        Word word1 = new Word("alpha");
        Word word2 = new Word("alpha");
        assertTrue(word1.equals(word2));
    }

    @Test
    public void equalsNotWord() {
        Word word1 = new Word("alpha");
        Word word2 = new Word("beta");
        assertFalse(word1.equals(word2));
    }

    @Test
    public void createRandomWord() {
        Word first = new Word(dictionary.getRandomWord());
        Word second = new Word(dictionary.getRandomWord());

        while (first.toString().equals(second.toString())) {
            first = new Word(dictionary.getRandomWord());
            second = new Word(dictionary.getRandomWord());
        }
        assertTrue(first.length() > 0);
        assertTrue(second.length() > 0);
        assertNotEquals(first.toString(), second.toString());
    }

    @Test
    public void testAdd() {
        word.add(new Letter(LetterEnum.X));
        assertEquals("testx", word.toString());
    }

    @Test
    public void testClear() {
        word.clear();
        assertEquals(0, word.length());
    }

    @Test
    public void testPosition() {

        assertEquals("s", word.get(2).toString());
    }

    @Test
    public void testLength() {
        assertEquals(4, word.length());
    }

    @Test
    public void testReverse() {
        assertEquals("tset", word.reverse().toString());
    }

    @Test
    public void testIterator() {
        StringBuilder sb = new StringBuilder();

        for (Letter letter : word) {
            sb.append(letter.getCharacter());
        }

        assertEquals("test", sb.toString());
    }
}