package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eng.game.items.Cannon;
import com.eng.game.logic.Pathfinding;
import com.eng.game.logic.ShipTable;
import com.eng.game.map.BackgroundTiledMap;
import com.eng.game.pathfinding.pathfinding.grid.GridCell;
import com.sun.tools.javac.util.Pair;

public class EnemyShip extends Ship {
    private final ShipTable shipTable;
    private final Pathfinding pathfinding;

    public EnemyShip(BackgroundTiledMap backgroundTiledMap, ShipTable shipTable, Pathfinding pathfinding) {
        // TODO: add enemy ship texture based on alliance
        super(backgroundTiledMap, new Texture("img/ship.png"), 100, 3, 200, backgroundTiledMap);
        addItem(new Cannon(10, 150, 2));
        this.shipTable = shipTable;
        this.pathfinding = pathfinding;
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

    @Override
    public String toString() {
        return "Enemy ship";
    }

    @Override
    public void act(float delta) {
        Pair<Ship, Float> target = shipTable.getClosestEnemyShip(this);
        Ship targetShip = target.fst;
        float distance = target.snd / tileWidth;
        if (targetShip == null || distance <= (float) getFiringRange() - 2) {
            Vector2 oldVelocities = velocity;
            float oldX = getX();
            float oldY = getY();
            super.act(delta);
            velocity.x = generateVelocity(oldVelocities.x, velocity.x);
            velocity.y = generateVelocity(oldVelocities.y, velocity.y);
        } else {
            Integer[] shipTileCoordinates = getTileCoordinates();
            Integer[] targetShipTileCoordinates = targetShip.getTileCoordinates();
            GridCell path = pathfinding.findPath(shipTileCoordinates[0], shipTileCoordinates[1], targetShipTileCoordinates[0], targetShipTileCoordinates[1]).get(0);
            if (path.getX() > shipTileCoordinates[0]) {
                velocity.x = speed;
            } else if (path.getX() < shipTileCoordinates[0]) {
                velocity.x = -speed;
            } else {
                velocity.x = 0;
            }
            if (path.getY() > shipTileCoordinates[1]) {
                velocity.y = speed;
            } else if (path.getY() < shipTileCoordinates[1]) {
                velocity.y = -speed;
            } else {
                velocity.y = 0;
            }
            super.act(delta);
        }

    }
}
