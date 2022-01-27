package com.eng.game;

import com.badlogic.gdx.Game;
import com.eng.game.screens.Play;

/**
 * Initializes the game.
 */
public class PirateGame extends Game {

    @Override
    public void create() {
        setScreen(new Play());
    }

}