/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz.word;

import at.beris.games.alphablockz.word.WordMatch;

import java.util.Comparator;

public class WordMatchLengthComparator implements Comparator<WordMatch> {

    public int compare(WordMatch o1, WordMatch o2) {
        return  Integer.compare(o2.getWord().length(), o1.getWord().length());
    }
}
