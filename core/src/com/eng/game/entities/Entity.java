package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.eng.game.actor.GameActor;
import com.eng.game.items.Cannon;
import com.eng.game.items.Item;
import com.eng.game.items.Key;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;
import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;

/**
 * Main actor class for all non-inanimate objects in the game.
 */
public abstract class Entity extends GameActor {
    private final ArrayList<Item> holding;
    private final int holdingCapacity;
    public int maxHealth;
    public int health;
    public Alliance alliance = Alliance.NEUTRAL;
    public boolean isDead = false;
    public int coins = 0;
    Integer movementRange = null;
    private int itemIndex = 0;

    public Entity(BackgroundTiledMap map, ActorTable actorTable, Texture texture, int maxHealth, int holdingCapacity) {
        super(map, actorTable, texture);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        if (holdingCapacity > 9) holdingCapacity = 9; // User can only select 1-9 items
        this.holding = new ArrayList<>(holdingCapacity);
        this.holdingCapacity = holdingCapacity;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public String getName() {
        return this.toString();
    }

    public abstract String toString();

    public Texture getTexture() {
        return texture;
    }

    public ArrayList<Item> getHolding() {
        return holding;
    }

    public Item getHeldItem() {
        try {
            return holding.get(itemIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void addItem(Item item) {
        holding.add(item);
    }

    /**
     * Picks up the item on the same tile as the entity.
     * If there is no item on the tile, nothing happens.
     * If the entity is holding an item, the item is dropped.
     *
     * @return true if an item was picked up, false otherwise
     */
    public boolean pickup() {
        Item item = actorTable.getItemInEntity(this);
        if (item == null) {
            System.out.println("No item on tile");
            return false;
        }
        item.onPickup();
        if (getHeldItem() != null) {
            drop();
        }
        addItem(item);
        return true;
    }


    /**
     * Drops the item at the entities item index.
     * The item is removed from the holding and placed on the map.
     * The item will be placed on an empty tile around the entity.
     *
     * @return true if the item was dropped, false otherwise
     */
    public boolean drop() {
        float x = getX();
        float y = getY();
        try {
            Item droppedItem = holding.remove(itemIndex);
            if (actorTable.getItemInEntity(this) != null) {
                Pair<Integer, Integer> emptyTile = droppedItem.findEmptyTile(getOriginX(), getOriginY());
                if (emptyTile == null) {
                    System.out.println("Entity.drop() - No empty tile found");
                    return false;
                }
                x = emptyTile.fst;
                y = emptyTile.snd;
            }

            droppedItem.setPosition(x, y);
            droppedItem.onDrop();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No item to drop");
            return false;
        }
        return true;
    }

    /**
     * Drops every item in the entity's holding.
     *
     * @return true if all items were dropped, false otherwise
     */
    public boolean dropAll() {
        boolean droppedAll = true;
        for (int i = 0; i < holding.size(); i++) {
            switchItem(i);
            boolean dropped = drop();
            if (!dropped) {
                droppedAll = false;
            }
        }
        return droppedAll;
    }

    /**
     * Switches the item in the entity's holding to the item at the given index.
     * If the index is out of bounds, nothing happens.
     * If the index is the same as the current item index, nothing happens.
     *
     * @param index the index of the item to switch to
     */
    public void switchItem(int index) {
        itemIndex = index;
    }

    public void useItem() {
        Item item = getHeldItem();
        if (item != null) {
            item.use();
        } else {
            System.out.println("No item to use");
        }
    }

    /**
     * Decreases the entity's health by the specified amount.
     * If the entity's health is 0 or less, it is considered dead.
     *
     * @param damage the amount of damage to deal
     */
    public void damage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }

    /**
     * Heals the entity by the specified amount.
     * Will only be healed up to the entity's max health.
     *
     * @param heal the amount to heal
     */
    public void heal(int heal) {
        health += heal;
        if (health > maxHealth) health = maxHealth;
    }

    /**
     * Kills the entity.
     * If the entity is a player, the game is over.
     * Entity is removed from the map.
     */
    public void die() {
        dropAll();
        isDead = true;
    }

    /**
     * Performs an action if supplied a valid key
     * By default, does nothing
     *
     * @param key the key to check
     * @return true if the key was valid, false otherwise
     */
    public boolean open(Key key) {
        System.out.println("Cannot be opened");
        return false;
    }

    public Integer getMovementRange() {
        return movementRange;
    }

    /**
     * @return the range of the cannon the entity is holding
     */
    public Integer getFiringRange() {
        for (Item item : holding) {
            // Change to weapon class?
            if (item instanceof Cannon) {
                return ((Cannon) item).getRange();
            }
        }
        return 0;
    }

    /**
     * Checks if coordinates are outside the enitites bounds
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true if coordinates are outside the entities bounds
     */
    public boolean isOutOfRange(float x, float y) {
        float xDiff = Math.abs(getX() - x);
        float yDiff = Math.abs(getY() - y);
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff) > getMovementRange();
    }

    public Alliance getAlliance() {
        return alliance;
    }


    public boolean isDead() {
        return isDead;
    }
}
