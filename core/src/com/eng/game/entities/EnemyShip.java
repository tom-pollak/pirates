package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Pathfinding;
import com.eng.game.map.BackgroundTiledMap;
import com.eng.game.pathfinding.pathfinding.grid.GridCell;
import com.sun.tools.javac.util.Pair;

import java.util.List;

public class EnemyShip extends Ship {
    private final Pathfinding pathfinding;

    public EnemyShip(BackgroundTiledMap map, ActorTable actorTable, Pathfinding pathfinding, Texture shipTexture) {
        // TODO: add enemy ship texture based on alliance
        super(map, actorTable, shipTexture, 100, 3, 375);
        this.pathfinding = pathfinding;
    }

    /**
     * Generates a velocity
     * If the ship is not moving, randomly choose a new velocity
     * If the ship sees an enemy ship, move towards it
     * If the ship is moving in the same velocity, keep the velocity or (sometimes) reverse it
     * If the ship has stopped, it has collided with a wall, so reverse direction
     *
     * @param velocity the current velocity on the x or y-axis
     * @return the new velocity
     */
    private float generateVelocity(float velocity) {
        if (velocity == 0) {
            if (Math.random() < 0.5) {
                return speed;
            } else {
                return -speed;
            }
        }
        try {
            // Bounding region for the ship
            if (alliance.getLeader().isOutOfRange(getX(), getY())) return -velocity;
        } catch (NullPointerException ignored) {
        }
        // Randomly change direction
        if (Math.random() < 0.02) {
            return -velocity;
        }
        return velocity;
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
        if (targetShip == null || tileDistance <= (float) getFiringRange() * 0.75f) {
            float oldX = getX(), oldY = getY();

            setX(getX() + velocity.x * delta);
            setY(getY() + velocity.y * delta);

            Pair<Boolean, Boolean> collisions = map.getMapCollisions(this, oldX, oldY);
            boolean collisionX = collisions.fst;
            boolean collisionY = collisions.snd;
            if (collisionX) {
                setX(oldX);
                velocity.x = -velocity.x;
            } else velocity.x = generateVelocity(velocity.x);

            if (collisionY) {
                setY(oldY);
                velocity.y = -velocity.y;
            } else velocity.y = generateVelocity(velocity.y);

        } else {
            // Pathfind to the target ship
            List<GridCell> searchPath = pathfinding.findPath(getTileX(), getTileY(), targetShip.getTileX(), targetShip.getTileY());
            if (searchPath == null) {
                super.act(delta);
                return;
            }

            // Navigate towards first tile in search path
            GridCell firstNode = searchPath.get(0);
            if (firstNode.getX() > getTileX()) {
                velocity.x = speed;
            } else if (firstNode.getX() < getTileX()) {
                velocity.x = -speed;
            } else {
                velocity.x = 0;
            }
            if (firstNode.getY() > getTileY()) {
                velocity.y = speed;
            } else if (firstNode.getY() < getTileY()) {
                velocity.y = -speed;
            } else {
                velocity.y = 0;
            }
            super.act(delta);
        }

    }
}
