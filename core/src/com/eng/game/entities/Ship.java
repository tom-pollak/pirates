package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eng.game.items.Cannon;
import com.eng.game.logic.ActorTable;
import com.eng.game.map.BackgroundTiledMap;

public class Ship extends Entity {

    public final float speed = 175;
    public Vector2 velocity = new Vector2();
    private float increment;

    public Ship(BackgroundTiledMap map, ActorTable actorTable, Texture texture, int health, int holdingCapacity, Integer movementRange) {
        super(map, texture, health, holdingCapacity);
        this.movementRange = movementRange;
        this.actorTable = actorTable;
        this.actorTable.addEntity(this);
        Cannon cannon = new Cannon(10, 6, 2, map);
        actorTable.addItem(cannon);
        addItem(cannon);
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
        increment = Math.min(map.getTileWidth(), getWidth()) / 2;

        if (velocity.x < 0)
            collisionX = collidesLeft();
        else if (velocity.x > 0)
            collisionX = collidesRight();

        if (collisionX) {
            setX(oldX);
            velocity.x = 0;
        }

        setY(getY() + velocity.y * delta);
        increment = Math.min(map.getTileHeight(), getHeight()) / 2;

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
            if (map.isTileBlocked(map.getTileX(getX() + getWidth()), map.getTileY(getY() + i))) return true;
        return false;
    }

    /**
     * @return true if the ship collides to a tile on the left
     */
    public boolean collidesLeft() {
        for (float i = 0; i <= getHeight(); i += increment)
            if (map.isTileBlocked(map.getTileX(getX()), map.getTileY(getY() + i))) return true;
        return false;
    }

    /**
     * @return true if the ship collides to a tile on the top
     */
    public boolean collidesTop() {
        for (float i = 0; i <= getWidth(); i += increment)
            if (map.isTileBlocked(map.getTileX(getX() + i), map.getTileY(getY() + getHeight()))) return true;
        return false;
    }

    /**
     * @return true if the ship collides to a tile on the bottom
     */
    public boolean collidesBottom() {
        for (float i = 0; i <= getWidth(); i += increment)
            if (map.isTileBlocked(map.getTileX(getX() + i), map.getTileY(getY()))) return true;
        return false;
    }

    public String toString() {
        return "Ship";
    }
}
