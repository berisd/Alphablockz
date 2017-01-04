/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz;

public interface GameScreenListener {
    void letterMoved();

    void letterFalling();

    void wordMatching();

    void gameStarted();

    void gameOver();

    void levelStarted();

    void levelCompleted();

    void exitGame();

    void gamePaused();
}
