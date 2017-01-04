/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz.word;

import at.beris.games.alphablockz.gui.Point;
import at.beris.games.alphablockz.word.Word;

public class WordMatch {
    Word word;
    Point location;
    Word.Alignment alignment;
    Word.Direction direction;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Word.Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Word.Alignment alignment) {
        this.alignment = alignment;
    }

    public Word.Direction getDirection() {
        return direction;
    }

    public void setDirection(Word.Direction direction) {
        this.direction = direction;
    }
}
