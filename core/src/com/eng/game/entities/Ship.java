package com.eng.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Ship extends Sprite {

    private final MapLayers layers;
    private final TiledMapTileLayer indexLayer;
    public Vector2 velocity = new Vector2();
    public float speed = 175;
    private float increment;

    public Ship(Sprite sprite, MapLayers layers) {
        super(sprite);
        this.layers = layers;
        indexLayer = (TiledMapTileLayer) layers.get(0);
//        setSize((float) (getWidth() * 0.5), (float) (getHeight() * 0.5));
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float delta) {

        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        setX(getX() + velocity.x * delta);

        // calculate the increment for collidesLeft and collidesRight
        increment = indexLayer.getTileWidth();
        increment = getWidth() < increment ? getWidth() / 2 : increment / 2;

        if (velocity.x < 0) // going left
            collisionX = collidesLeft();
        else if (velocity.x > 0) // going right
            collisionX = collidesRight();

        // react to x collision
        if (collisionX) {
            setX(oldX);
            velocity.x = 0;
        }

        // move on y
        setY(getY() + velocity.y * delta);

        // calculate the increment for collidesBottom and collidesTop
        increment = indexLayer.getTileHeight();
        increment = getHeight() < increment ? getHeight() / 2 : increment / 2;

        if (velocity.y < 0) // going down
            collisionY = collidesBottom();
        else if (velocity.y > 0) // going up
            collisionY = collidesTop();

        // react to y collision
        if (collisionY) {
            setY(oldY);
            velocity.y = 0;
        }
    }

    private boolean isCellBlocked(float x, float y) {
        for (int i = 0; i < layers.getCount(); i++) {
            TiledMapTileLayer collisionLayer = (TiledMapTileLayer) layers.get(i);
            TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));

            if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked"))
                return true;
        }
        return false;
    }

    public boolean collidesRight() {
        for (float i = 0; i <= getHeight(); i += increment)
            if (isCellBlocked(getX() + getWidth(), getY() + i))
                return true;
        return false;
    }

    public boolean collidesLeft() {
        for (float i = 0; i <= getHeight(); i += increment)
            if (isCellBlocked(getX(), getY() + i))
                return true;
        return false;
    }

    public boolean collidesTop() {
        for (float i = 0; i <= getWidth(); i += increment)
            if (isCellBlocked(getX() + i, getY() + getHeight()))
                return true;
        return false;

    }

    public boolean collidesBottom() {
        for (float i = 0; i <= getWidth(); i += increment)
            if (isCellBlocked(getX() + i, getY()))
                return true;
        return false;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public TiledMapTileLayer getIndexLayer() {
        return indexLayer;
    }
}
