package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eng.game.items.Cannon;
import com.eng.game.map.BackgroundTiledMap;

public class EnemyShip extends Ship {
    public EnemyShip(BackgroundTiledMap backgroundTiledMap) {
        // TODO: add enemy ship texture based on alliance
        super(new Texture("img/ship.png"), 100, 3, backgroundTiledMap);
        addItem(new Cannon(10, 6, 2));
    }

    private float generateVelocity(float oldVelocity, float newVelocity) {
        if (oldVelocity == 0) {
            if (Math.random() < 0.5) {
                return speed;
            } else {
                return -speed;
            }
        }
        if (newVelocity == 0) {
            return -speed;
        }

        try {
            if (alliance.getLeader().isOutOfRange(getX(), getY())) {
                return -newVelocity;
            }
        } catch (NullPointerException ignored) {
        }
        if (Math.random() < 0.01) {
            return -newVelocity;
        }
        return newVelocity;
    }

    // TODO: Add movement and pathfinding (AI)

    @Override
    public String toString() {
        return "Enemy ship";
    }

    @Override
    public void act(float delta) {
        Vector2 oldVelocities = velocity;
        float oldX = getX();
        float oldY = getY();
        super.act(delta);
        velocity.x = generateVelocity(oldVelocities.x, velocity.x);
        velocity.y = generateVelocity(oldVelocities.y, velocity.y);
    }
}
