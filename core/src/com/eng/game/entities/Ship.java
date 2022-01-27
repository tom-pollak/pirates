package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.eng.game.items.Cannon;
import com.eng.game.map.BackgroundTiledMap;

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

    /**
     * Updates ships position
     * Calculates the ships position based on the velocity and delta time
     * If the ship moves inside a blocked tile, the ship is moved back to the previous position and the velocity is set to 0
     *
     * @param delta time since last update
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        setX(getX() + velocity.x * delta);
        increment = Math.min(getTileWidth(), getWidth()) / 2;

        if (velocity.x < 0)
            collisionX = collidesLeft();
        else if (velocity.x > 0)
            collisionX = collidesRight();

        if (collisionX) {
            setX(oldX);
            velocity.x = 0;
        }

        setY(getY() + velocity.y * delta);
        increment = Math.min(getTileHeight(), getHeight()) / 2;

        if (velocity.y < 0)
            collisionY = collidesBottom();
        else if (velocity.y > 0)
            collisionY = collidesTop();

        if (collisionY) {
            setY(oldY);
            velocity.y = 0;
        }
    }

    /**
     * @return true if the ship collides to a tile on the right
     */
    public boolean collidesRight() {
        for (float i = 0; i <= getHeight(); i += increment)
            if (backgroundTiledMap.isCellBlocked(getX() + getWidth(), getY() + i)) return true;
        return false;
    }

    /**
     * @return true if the ship collides to a tile on the left
     */
    public boolean collidesLeft() {
        for (float i = 0; i <= getHeight(); i += increment)
            if (backgroundTiledMap.isCellBlocked(getX(), getY() + i)) return true;
        return false;
    }

    /**
     * @return true if the ship collides to a tile on the top
     */
    public boolean collidesTop() {
        for (float i = 0; i <= getWidth(); i += increment)
            if (backgroundTiledMap.isCellBlocked(getX() + i, getY() + getHeight())) return true;
        return false;
    }

    /**
     * @return true if the ship collides to a tile on the bottom
     */
    public boolean collidesBottom() {
        for (float i = 0; i <= getWidth(); i += increment)
            if (backgroundTiledMap.isCellBlocked(getX() + i, getY())) return true;
        return false;
    }

    public String toString() {
        return "Ship";
    }
}
