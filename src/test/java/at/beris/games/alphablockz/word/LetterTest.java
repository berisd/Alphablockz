/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz.word;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class LetterTest {
    Letter letter;

    @Before
    public void setup() {
        letter = new Letter('l');
    }

    @Test
    public void cloneLetter() {
        Letter clone = (Letter) letter.clone();

        assertNotSame(letter, clone);
    }

    @Test
    public void createLetterFromChar() {
        assertEquals("l", letter.toString());
    }


}