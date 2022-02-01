package com.eng.game.logic;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.eng.game.entities.Entity;
import com.eng.game.entities.Player;
import com.eng.game.entities.Ship;
import com.eng.game.items.Item;
import com.eng.game.map.BackgroundTiledMap;
import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;

/**
 * Is a repository for all actors in the game.
 */
public class ActorTable {
    private final ArrayList<Item> items = new ArrayList<>();
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final Stage stage;
    private final BackgroundTiledMap map;
    private Player player;

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

    public void addActor(Entity entity) {
        if (entity instanceof Player) {
            this.player = (Player) entity;
        }
        if (entity.getStage() == null) {
            stage.addActor(entity);
        }
        entities.add(entity);
    }

    public Player getPlayer() {
        return player;
    }

    public void addActor(Item item) {
        if (item.getStage() == null) {
            stage.addActor(item);
        }
        items.add(item);
    }

    public void removeActor(Entity entity) {
        entities.remove(entity);
    }

    public void removeActor(Item item) {
        items.remove(item);
    }

    public ArrayList<Ship> getShips() {
        ArrayList<Ship> ships = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Ship) {
                ships.add((Ship) entity);
            }
        }
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
        ArrayList<Entity> entitiesOnTile = new ArrayList<>();
        for (Entity collidingEntity : entities) {
            if (map.isCollision(entity.getHitbox(), collidingEntity.getHitbox()) && !collidingEntity.equals(entity)) {
                entitiesOnTile.add(collidingEntity);
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
    public Pair<Ship, Float> getClosestEnemyShip(Entity ship) {
        if (ship.getAlliance().getLeader() == null) {
            System.out.println("no leader");
            return null;
        }
        Ship closest = null;
        float distance = Float.MAX_VALUE;
        for (Entity newShip : entities) {
            if (!(newShip instanceof Ship) || ship.getAlliance().isAlly(newShip) || ship.isOutOfRange(newShip.getX(), newShip.getY()) || ship.getAlliance().getLeader().isOutOfRange(newShip.getX(), newShip.getY())) {
                continue;
            }

            float d = (float) Math.sqrt(Math.pow(ship.getX() - newShip.getX(), 2) + Math.pow(ship.getY() - newShip.getY(), 2));
            if (d < distance) {
                distance = d;
                closest = (Ship) newShip;
            }
        }
        return new Pair<>(closest, distance / map.getTileWidth());
    }
}
