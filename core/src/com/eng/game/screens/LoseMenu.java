package com.eng.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.eng.game.PirateGame;

public class LoseMenu implements Screen {

    PirateGame game;
    private final BitmapFont font;
    private final Texture background;

    private final int backgroundx;
    private final int backgroundy;

    public LoseMenu(PirateGame game){
        this.game = game;


        background = new Texture("img/Background.PNG");
        backgroundx = (int)(background.getWidth() * 1.1);
        backgroundy = (int)(background.getHeight() / 1.1);

        font = new BitmapFont();
        font.setColor(1, 0,0, 1);
        font.getData().setScale(backgroundx / 300, backgroundy / 300);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw Losing message
        game.batch.begin();
        game.batch.draw(background, 0, 0, backgroundx, backgroundy);
        font.draw(game.batch, "You lose", backgroundx / 2 - 75, backgroundy / 10 * 8);
        font.draw(game.batch, "Press Space to return to the main menu", backgroundx / 11, backgroundy / 10 * 5);
        game.batch.end();

        // change scene when space bar is pressed
        //if ((Gdx.input.isKeyPressed(Input.Keys.SPACE))){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            this.dispose();
            game.setScreen(new MainMenu((game)));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();

    }
}
