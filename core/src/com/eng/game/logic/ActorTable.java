package com.eng.game.logic;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.eng.game.entities.College;
import com.eng.game.entities.Entity;
import com.eng.game.entities.Ship;
import com.eng.game.entities.TreasureChest;
import com.eng.game.items.Item;
import com.eng.game.map.BackgroundTiledMap;
import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;

/**
 * Is a repository for all actors in the game.
 */
public class ActorTable {
    private final ArrayList<Item> items = new ArrayList<>();
    private final ArrayList<Ship> ships = new ArrayList<>();
    private final ArrayList<College> colleges = new ArrayList<>();
    private final ArrayList<TreasureChest> treasureChests = new ArrayList<>();
    private final Stage stage;
    private final BackgroundTiledMap map;

    public ActorTable(Stage stage, BackgroundTiledMap map) {
        this.stage = stage;
        this.map = map;
    }

    public boolean willItemCollide(Item item, float x, float y) {
        for (Item checkItem : items) {
            if (map.isCollision(item.getHitbox(), checkItem.getHitbox())) {
                return true;
            }
        }
        return false;
    }

    public void addActor(Ship ship) {
        if (ship.getStage() == null) {
            stage.addActor(ship);
        }
        ships.add(ship);
    }

    public void addActor(College college) {
        if (college.getStage() == null) {
            stage.addActor(college);
        }
        colleges.add(college);
    }

    public void addActor(TreasureChest treasureChest) {
        if (treasureChest.getStage() == null) {
            stage.addActor(treasureChest);
        }
        treasureChests.add(treasureChest);
    }

    public void addActor(Item item) {
        if (item.getStage() == null) {
            stage.addActor(item);
        }
        items.add(item);
    }

    public void removeActor(College college) {
        colleges.remove(college);
    }

    public void removeActor(Ship ship) {
        ships.remove(ship);
    }

    public void removeActor(TreasureChest treasureChest) {
        treasureChests.remove(treasureChest);
    }

    public void removeActor(Item item) {
        items.remove(item);
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Item getItemInEntity(Entity entity) {

        for (Item item : items) {
            if (map.isCollision(entity.getHitbox(), item.getHitbox()) && !item.isHeld) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<Entity> getCollidingEntities(Entity entity) {
        ArrayList<Entity> entities = new ArrayList<>();
        ArrayList<Entity> entitiesOnTile = new ArrayList<>();
        entities.addAll(ships);
        entities.addAll(colleges);
        entities.addAll(treasureChests);
        for (Entity collidingEntity : entities) {
            if (map.isCollision(entity.getHitbox(), collidingEntity.getHitbox())) {
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
        return new Pair<>(closest, distance / map.getTileWidth());
    }
}
