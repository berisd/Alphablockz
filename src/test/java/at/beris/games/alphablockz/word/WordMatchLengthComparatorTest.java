/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz.word;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WordMatchLengthComparatorTest {
    WordMatchLengthComparator comparator;

    @Before
    public void setup() {
        comparator = new WordMatchLengthComparator();
    }

    @Test
    public void sortWordMatchesbyWordLengthDescending() {
        List<WordMatch> wordMatches = new ArrayList<WordMatch>();

        WordMatch wordMatch = new WordMatch();
        wordMatch.setWord(new Word("abc"));
        wordMatches.add(wordMatch);
        wordMatch = new WordMatch();
        wordMatch.setWord(new Word("abcde"));
        wordMatches.add(wordMatch);
        wordMatch = new WordMatch();
        wordMatch.setWord(new Word("abcd"));
        wordMatches.add(wordMatch);
        Collections.sort(wordMatches, new WordMatchLengthComparator());

        assertEquals("abcde", wordMatches.get(0).getWord().toString());
        assertEquals("abcd", wordMatches.get(1).getWord().toString());
        assertEquals("abc", wordMatches.get(2).getWord().toString());
    }
}