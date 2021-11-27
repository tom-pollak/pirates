package com.eng.game;

import com.badlogic.gdx.Game;
import com.eng.game.screens.Play;

public class PirateGame extends Game {

    @Override
    public void create() {
        setScreen(new Play());
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        super.render();
    }
}