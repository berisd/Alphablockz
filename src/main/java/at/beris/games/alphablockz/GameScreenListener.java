/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
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
