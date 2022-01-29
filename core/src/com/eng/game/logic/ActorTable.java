package com.eng.game.logic;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.eng.game.entities.College;
import com.eng.game.entities.Entity;
import com.eng.game.entities.Ship;
import com.eng.game.entities.TreasureChest;
import com.eng.game.items.Item;
import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;

/**
 * Contains all the ships currently in the game.
 */
public class ActorTable {
    private final ArrayList<Item> items = new ArrayList<>();
    private final ArrayList<Ship> ships = new ArrayList<>();
    private final ArrayList<College> colleges = new ArrayList<>();
    private final ArrayList<TreasureChest> treasureChests = new ArrayList<>();
    private final Stage stage;

    public ActorTable(Stage stage) {
        this.stage = stage;
    }

    public void addEntity(Ship ship) {
        if (ship.getStage() == null) {
            stage.addActor(ship);
        }
        ships.add(ship);
    }

    public void addEntity(College college) {
        if (college.getStage() == null) {
            stage.addActor(college);
        }
        colleges.add(college);
    }

    public void addEntity(TreasureChest treasureChest) {
        if (treasureChest.getStage() == null) {
            stage.addActor(treasureChest);
        }
        treasureChests.add(treasureChest);
    }

    public void removeEntity(College college) {
        colleges.remove(college);
    }

    public void removeEntity(Ship ship) {
        ships.remove(ship);
    }

    public void removeEntity(TreasureChest treasureChest) {
        treasureChests.remove(treasureChest);
    }

    public void addItem(Item item) {
        if (item.getStage() == null) {
            stage.addActor(item);
        }
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Item getItemOnTile(int tileX, int tileY) {
        for (Item item : items) {
            if (item.getTileX() == tileX && item.getTileY() == tileY && !item.isHeld) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<Entity> getEntitiesOnTile(int tileX, int tileY) {
        ArrayList<Entity> entities = new ArrayList<>();
        ArrayList<Entity> entitiesOnTile = new ArrayList<>();
        entities.addAll(ships);
        entities.addAll(colleges);
        entities.addAll(treasureChests);
        for (Entity entity : entities) {
            if (entity.getTileX() == tileX && entity.getTileY() == tileY) {
                entitiesOnTile.add(entity);
            }
        }
        return entitiesOnTile;
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
        return new Pair<>(closest, distance / ship.map.getTileWidth());
    }
}
