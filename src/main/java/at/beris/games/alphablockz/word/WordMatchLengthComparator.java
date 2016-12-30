/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz.word;

import at.beris.games.alphablockz.word.WordMatch;

import java.util.Comparator;

public class WordMatchLengthComparator implements Comparator<WordMatch> {

    public int compare(WordMatch o1, WordMatch o2) {
        return  Integer.compare(o2.getWord().length(), o1.getWord().length());
    }
}
