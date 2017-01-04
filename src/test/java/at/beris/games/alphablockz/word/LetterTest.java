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