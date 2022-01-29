package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eng.game.items.Cannon;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Pathfinding;
import com.eng.game.map.BackgroundTiledMap;
import com.eng.game.pathfinding.pathfinding.grid.GridCell;
import com.sun.tools.javac.util.Pair;

public class EnemyShip extends Ship {
    private final Pathfinding pathfinding;

    public EnemyShip(BackgroundTiledMap map, ActorTable actorTable, Pathfinding pathfinding) {
        // TODO: add enemy ship texture based on alliance
        super(map, actorTable, new Texture("img/ship.png"), 100, 3, 200);
        addItem(new Cannon(10, 150, 2, map));
        this.pathfinding = pathfinding;
    }

    /**
     * Generates a velocity
     * If the ship is not moving, randomly choose a new velocity
     * If the ship sees an enemy ship, move towards it
     * If the ship is moving in the same velocity, keep the velocity or (sometimes) reverse it
     * If the ship has stopped, it has collided with a wall, so reverse direction
     *
     * @param oldVelocity the old velocity
     * @param newVelocity velocity after checking for collisions
     * @return the new velocity
     */
    private float generateVelocity(float oldVelocity, float newVelocity) {
        //
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
            // Bounding region for the ship
            if (alliance.getLeader().isOutOfRange(getX(), getY())) return -newVelocity;
        } catch (NullPointerException ignored) {
        }
        // Randomly change direction
        if (Math.random() < 0.01) {
            return -newVelocity;
        }
        return newVelocity;
    }

    @Override
    public String toString() {
        return "Enemy ship";
    }

    /**
     * Updates the ship's velocity
     *
     * @param delta the time since the last update
     */
    @Override
    public void act(float delta) {
        Pair<Ship, Float> target = actorTable.getClosestEnemyShip(this);
        Ship targetShip = target.fst;
        float tileDistance = target.snd;

        // If ship is in firing range, move randomly like before
        if (targetShip == null || tileDistance <= (float) getFiringRange() - 2) {
            Vector2 oldVelocities = velocity;
            super.act(delta);
            velocity.x = generateVelocity(oldVelocities.x, velocity.x);
            velocity.y = generateVelocity(oldVelocities.y, velocity.y);
        } else {
            // Pathfind to the target ship
            GridCell searchPath = pathfinding.findPath(getTileX(), getTileY(), targetShip.getTileX(), targetShip.getTileY()).get(0);

            // Navigate towards first tile in search path
            if (searchPath.getX() > getTileX()) {
                velocity.x = speed;
            } else if (searchPath.getX() < getTileX()) {
                velocity.x = -speed;
            } else {
                velocity.x = 0;
            }
            if (searchPath.getY() > getTileY()) {
                velocity.y = speed;
            } else if (searchPath.getY() < getTileY()) {
                velocity.y = -speed;
            } else {
                velocity.y = 0;
            }
            super.act(delta);
        }

    }
}
