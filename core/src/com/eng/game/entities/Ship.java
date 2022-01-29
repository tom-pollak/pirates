package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.eng.game.items.Cannon;
import com.eng.game.map.BackgroundTiledMap;
import com.sun.tools.javac.util.Pair;

public class Ship extends Entity {

    public final float speed = 175;
    private final Texture texture;
    private final BackgroundTiledMap backgroundTiledMap;
    public Vector2 velocity = new Vector2();
    private float increment;

    public Ship(BackgroundTiledMap tiledMap, Texture texture, int health, int holdingCapacity, Integer movementRange, BackgroundTiledMap backgroundTiledMap) {
        super(tiledMap, texture, health, holdingCapacity);
        this.texture = texture;
        this.backgroundTiledMap = backgroundTiledMap;
        this.movementRange = movementRange;
        addItem(new Cannon(10, 6, 2));
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        float oldX = getX(), oldY = getY();

        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);

        Pair<Boolean, Boolean> collisions = backgroundTiledMap.getCollisions(this, oldX, oldY);
        boolean collisionX = collisions.fst;
        boolean collisionY = collisions.snd;

        if (collisionX) {
            setX(oldX);
            velocity.x = 0;
        }
        if (collisionY) {
            setY(oldY);
            velocity.y = 0;
        }
    }


    public String toString() {
        return "Ship";
    }
}
