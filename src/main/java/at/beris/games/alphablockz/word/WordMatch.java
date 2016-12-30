/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
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
