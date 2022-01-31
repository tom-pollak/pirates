package com.eng.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.eng.game.PirateGame;
import com.eng.game.entities.College;
import com.eng.game.entities.EnemyShip;
import com.eng.game.entities.Player;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Pathfinding;
import com.eng.game.map.BackgroundTiledMap;


/**
 * Main class that implements the game screen
 */
public class Play implements Screen {

    private final Stage stage = new Stage();
    PirateGame game;
    private Player player;
    private EnemyShip enemyShip;

    private final SpriteBatch batch;
    private final BitmapFont font;
    private int timer;
    private float timeCounter;

    public Play(PirateGame game) {
        this.game = game;
        timer = 600;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(1, 1,1, 1);
    }

    /**
     * Initializes the game screen
     */
    @Override
    public void show() {
        Pathfinding pathfinding = new Pathfinding();
        BackgroundTiledMap backgroundTiledMap = new BackgroundTiledMap(stage);
        stage.addActor(backgroundTiledMap);

        ActorTable actorTable = new ActorTable(stage, backgroundTiledMap);

        College college = new College(backgroundTiledMap, actorTable, "James", 100, 3, 1000);

        college.setPosition(5 * backgroundTiledMap.getTileWidth(), 13 * backgroundTiledMap.getTileHeight());
        System.out.println(college + " " + college.getAlliance());

        Gdx.input.setInputProcessor(stage);
        player = new Player(backgroundTiledMap, actorTable);
        player.setPosition(4 * backgroundTiledMap.getTileWidth(), 13 * backgroundTiledMap.getTileHeight());

        stage.setKeyboardFocus(player);
        player.addListener(player.input);

        enemyShip = new EnemyShip(backgroundTiledMap, actorTable, pathfinding);
        enemyShip.setPosition(5 * backgroundTiledMap.getTileWidth(), 13 * backgroundTiledMap.getTileHeight());
        enemyShip.setSize(5, 10);
        college.getAlliance().addAlly(enemyShip);
    }

    /**
     * Updates the screen
     *
     * @param delta time since last update
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(150f / 255f, 238f / 255f , 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        stage.getCamera().position.set(player.getOriginX(), player.getOriginY(), 0);
        stage.getCamera().update();

        // Makes the timer count down
        timeCounter += delta;
        if (timeCounter >= 1){
            timeCounter = 0;
            timer -= 1;
        }

        // Draw the timer
        batch.begin();
        font.draw(batch, "Time: " + timer, stage.getWidth() / 100 * 89, stage.getHeight() / 100 * 98);
        batch.end();

        // End if timer reaches zero
        if (timer == 0){
            this.dispose();
            game.setScreen(new LoseMenu(game));
        }
    }


    @Override
    public void dispose() {
        //backgroundTiledMap.dispose();
        player.getTexture().dispose();
        enemyShip.getTexture().dispose();
        font.dispose();
        //batch.dispose();
    }

    /**
     * Resizes the screen and scales the camera accordingly
     *
     * @param width:  new screen width
     * @param height: new screen height
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().viewportWidth = width / 1.5f;
        stage.getCamera().viewportHeight = height / 1.5f;
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


