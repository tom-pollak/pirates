package com.eng.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.eng.game.PirateGame;
import com.eng.game.entities.College;
import com.eng.game.entities.EnemyShip;
import com.eng.game.entities.Player;
import com.eng.game.entities.TreasureChest;
import com.eng.game.items.Key;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Pathfinding;
import com.eng.game.map.BackgroundTiledMap;
import com.sun.tools.javac.util.Pair;


/**
 * Main class that implements the game screen
 */
public class Play implements Screen {

    public static Player player;
    private static int timer;
    private final Stage stage = new Stage();
    private final SpriteBatch batch;
    private final BitmapFont font;
    PirateGame game;
    private EnemyShip enemyShip;
    private float timeCounter;

    public Play(PirateGame game) {
        this.game = game;
        Gdx.input.setInputProcessor(stage);
        setTimer(600);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(1, 1, 1, 1);
    }

    public static int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        Play.timer = timer;
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

        player = new Player(backgroundTiledMap, actorTable);
        player.setPosition(698, 2560);
        stage.setKeyboardFocus(player);
        player.addListener(player.input);

        // Player college
        new College(backgroundTiledMap, actorTable, pathfinding, new Texture("img/james.png"), "James", 100, 3, 1500, new Pair<>(698, 2560), 0).setPosition(704, 2765);

        // Enemy colleges
        new College(backgroundTiledMap, actorTable, pathfinding, new Texture("img/halifax.png"), "Halifax", 100, 3, 1500, new Pair<>(394, 367), 3).setPosition(579, 216);
        new College(backgroundTiledMap, actorTable, pathfinding, new Texture("img/constantine.png"), "Constantine", 100, 3, 1500, new Pair<>(2590, 642), 4).setPosition(2704, 463);
        new College(backgroundTiledMap, actorTable, pathfinding, new Texture("img/alcuin.png"), "Alcuin", 100, 3, 1500, new Pair<>(2674, 2497), 5).setPosition(2744, 2711);


        // Treasure chests
        TreasureChest chest = new TreasureChest(backgroundTiledMap, actorTable, "Glistening treasure");
        chest.setPosition(1698, 1308);
        Key key = chest.generateKey();
        key.setPosition(2800, 240);

    }

    /**
     * Updates the screen
     *
     * @param delta time since last update
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(150f / 255f, 238f / 255f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        stage.getCamera().position.set(player.getOriginX(), player.getOriginY(), 0);
        stage.getCamera().update();

        // Makes the timer count down
        timeCounter += delta;
        if (timeCounter >= 1) {
            timeCounter = 0;
            setTimer(getTimer() - 1);
        }

        // Draw the timer
        batch.begin();
        font.draw(batch, "Time: " + getTimer(), stage.getWidth() / 100 * 89, stage.getHeight() / 100 * 98);
        batch.end();

        // End game if timer reaches zero or players health reaches zero
        if (getTimer() == 0 || player.health == 0) {
            this.dispose();
            game.setScreen(new LoseMenu(game));
        }
    }

    @Override
    public void dispose() {
        player.getTexture().dispose();
        enemyShip.getTexture().dispose();
        font.dispose();
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


