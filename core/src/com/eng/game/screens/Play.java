package com.eng.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.eng.game.PirateGame;
import com.eng.game.entities.EnemyShip;
import com.eng.game.entities.Player;
import com.eng.game.map.BackgroundTiledMap;


public class Play implements Screen {

    private final Stage stage = new Stage();
    public BackgroundTiledMap backgroundTiledMap;
    private Player player;
    private EnemyShip enemyShip;
    PirateGame game;

    public Play(PirateGame game) {
        this.game = game;
    }
    @Override
    public void show() {
        backgroundTiledMap = new BackgroundTiledMap(stage);
        stage.addActor(backgroundTiledMap);

        Gdx.input.setInputProcessor(stage);
        player = new Player(backgroundTiledMap);
        player.setPosition(4 * backgroundTiledMap.getTileWidth(), 13 * backgroundTiledMap.getTileHeight());
        // TODO: work out how to make the player's size proportional to the screen size
        // player.setSize(0.2f * stage.getWidth(), 0.2f * stage.getWidth() * player.getHeight() / player.getWidth());
        player.setSize(5, 10);

        stage.setKeyboardFocus(player);
        player.addListener(player.input);
        stage.addActor(player);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        stage.getCamera().position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        stage.getCamera().update();
    }


    @Override
    public void dispose() {
        backgroundTiledMap.dispose();
        player.getTexture().dispose();
    }

    @Override
    public void resize(int width, int height) {
        // TODO: get the right camera zoom
//        camera.viewportWidth = width / 2.5f;
//        camera.viewportHeight = height / 2.5f;

        stage.getViewport().update(width, height);
        stage.getCamera().viewportWidth = width / 2.5f;
        stage.getCamera().viewportHeight = height / 2.5f;
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2, stage.getCamera().viewportHeight / 2, 0);
        stage.getCamera().update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();

    }

}
