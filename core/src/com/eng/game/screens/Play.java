package com.eng.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.eng.game.entities.College;
import com.eng.game.entities.EnemyShip;
import com.eng.game.entities.Player;
import com.eng.game.logic.Pathfinding;
import com.eng.game.logic.ShipTable;
import com.eng.game.map.BackgroundTiledMap;


public class Play implements Screen {

    private final Stage stage = new Stage();
    private Player player;
    private BackgroundTiledMap backgroundTiledMap;
    private EnemyShip enemyShip;

    @Override
    public void show() {
        Pathfinding pathfinding = new Pathfinding();
        ShipTable shipTable = new ShipTable();
        backgroundTiledMap = new BackgroundTiledMap(stage);
        stage.addActor(backgroundTiledMap);

        College college = new College(backgroundTiledMap, "James", 100, 3, 1000);

        stage.addActor(college);
        college.setPosition(5 * backgroundTiledMap.getTileWidth(), 13 * backgroundTiledMap.getTileHeight());
        System.out.println(college + " " + college.getAlliance());

        Gdx.input.setInputProcessor(stage);
        player = new Player(backgroundTiledMap, shipTable);
        player.setPosition(4 * backgroundTiledMap.getTileWidth(), 13 * backgroundTiledMap.getTileHeight());

        stage.setKeyboardFocus(player);
        player.addListener(player.input);
        stage.addActor(player);

//        enemyShip = new EnemyShip(backgroundTiledMap, shipTable, pathfinding);
//        enemyShip.setPosition(5 * backgroundTiledMap.getTileWidth(), 13 * backgroundTiledMap.getTileHeight());
//        enemyShip.setSize(5, 10);
//        college.getAlliance().addAlly(enemyShip);
//        stage.addActor(enemyShip);
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
        enemyShip.getTexture().dispose();
    }

    @Override
    public void resize(int width, int height) {
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


