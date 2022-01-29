package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eng.game.items.Cannon;
import com.eng.game.logic.ActorTable;
import com.eng.game.map.BackgroundTiledMap;
import com.sun.tools.javac.util.Pair;

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
        setY(getY() + velocity.y * delta);

        Pair<Boolean, Boolean> collisions = backgroundTiledMap.getCollisions(this, oldX, oldY);
        boolean collisionX = collisions.fst;
        boolean collisionY = collisions.snd;

        if (collisionX) {
            setX(oldX);
            velocity.x = 0;
        }

        // move on y
        setY(getY() + velocity.y * delta);


        // calculate the increment for collidesBottom and collidesTop
        increment = backgroundTiledMap.getTileHeight();
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

    public boolean collidesRight() {
        for (float i = 0; i <= getHeight(); i += increment)
            if (backgroundTiledMap.isCellBlocked(getX() + getWidth(), getY() + i)) return true;
        return false;
    }

    public boolean collidesLeft() {
        for (float i = 0; i <= getHeight(); i += increment)
            if (backgroundTiledMap.isCellBlocked(getX(), getY() + i)) return true;
        return false;
    }

    public boolean collidesTop() {
        for (float i = 0; i <= getWidth(); i += increment)
            if (backgroundTiledMap.isCellBlocked(getX() + i, getY() + getHeight())) return true;
        return false;
    }

    public boolean collidesBottom() {
        for (float i = 0; i <= getWidth(); i += increment)
            if (backgroundTiledMap.isCellBlocked(getX() + i, getY())) return true;
        return false;
    }

    public String toString() {
        return "Ship";
    }
}
