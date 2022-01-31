package com.eng.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eng.game.screens.MainMenu;
import com.eng.game.screens.WinMenu;

/**
 * Initializes the game.
 */
public class PirateGame extends Game {

    public SpriteBatch batch;

    /**
     * Renders the different screens
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        // change this to open on a new screen
        setScreen(new MainMenu(this));
    }
}