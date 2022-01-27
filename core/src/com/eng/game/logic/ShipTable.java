package com.eng.game.logic;

import com.eng.game.entities.Ship;
import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;

/**
 * Contains all the ships currently in the game.
 */
public class ShipTable {
    ArrayList<Ship> ships = new ArrayList<>();

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public void removeShip(Ship ship) {
        ships.remove(ship);
    }


    /**
     * Gets the closest enemy ship to the given ship.
     *
     * @param ship: The original ship.
     * @return: The closest enemy ship, the tile distance between the ships.
     */
    public Pair<Ship, Float> getClosestEnemyShip(Ship ship) {
        Ship closest = null;
        float distance = Float.MAX_VALUE;
        for (Ship newShip : ships) {
            if (ship.getAlliance().isAlly(newShip) || ship.isOutOfRange(newShip.getX(), newShip.getY()) || ship.getAlliance().getLeader().isOutOfRange(newShip.getX(), newShip.getY())) {
                continue;
            }

            float d = (float) Math.sqrt(Math.pow(ship.getX() - newShip.getX(), 2) + Math.pow(ship.getY() - newShip.getY(), 2));
            if (d < distance) {
                distance = d;
                closest = newShip;
            }
        }
        return new Pair<>(closest, distance / ship.getTileWidth());
    }
}
