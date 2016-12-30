/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package at.beris.games.alphablockz;

import at.beris.games.alphablockz.word.Word;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GameScreenTest {

    GameScreen gameScreen;

    @Before
    public void setup() {
        gameScreen = new GameScreen(new GameScreenAdapter() {
        }, new GameLogic());
    }






}
