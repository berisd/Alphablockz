/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */
package at.beris.games.alphablockz;

import org.junit.Before;

public class GameScreenTest {

    GameScreen gameScreen;

    @Before
    public void setup() {
        gameScreen = new GameScreen(new GameScreenAdapter() {
        }, new GameLogic());
    }

}
