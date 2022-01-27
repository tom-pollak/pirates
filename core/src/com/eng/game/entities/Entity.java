package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.eng.game.items.Cannon;
import com.eng.game.items.Item;
import com.eng.game.items.Key;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;

import java.util.ArrayList;

/**
 * Main actor class for all non-inanimate objects in the game.
 */
public abstract class Entity extends Actor {
    private final ArrayList<Item> holding;
    private final int holdingCapacity;
    private final Texture texture;
    public int maxHealth;
    public int health;
    public Alliance alliance = Alliance.NEUTRAL;
    public boolean isDead = false;
    public int coins = 0;
    protected int tileWidth;
    protected int tileHeight;
    Integer movementRange = null;
    private int itemIndex = 0;


    Entity(BackgroundTiledMap tiledMap, Texture texture, int maxHealth, int holdingCapacity) {
        this.texture = texture;
        this.setWidth(texture.getWidth());
        this.setHeight(texture.getHeight());
        this.setBounds(0, 0, texture.getWidth(), texture.getHeight());
        this.setOrigin(getWidth() / 2, getHeight() / 2);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        if (holdingCapacity > 9) holdingCapacity = 9; // User can only select 1-9 items
        this.holding = new ArrayList<>(holdingCapacity);
        this.holdingCapacity = holdingCapacity;
        this.tileWidth = tiledMap.getTileWidth();
        this.tileHeight = tiledMap.getTileHeight();
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

    /**
     * Adds an item to the entity's holding.
     * If the holding is full, the item is dropped.
     *
     * @param item the item to add
     * @return true if the item was added, false otherwise
     */
    public boolean addItem(Item item) {
        if (holding.size() == holdingCapacity) {
            return false;
        }
        holding.add(item);
        // Use return value to check if item was added
        return true;
    }

    /**
     * Picks up the item on the same tile as the entity.
     * If there is no item on the tile, nothing happens.
     * If the entity is holding an item, the item is dropped.
     *
     * @return true if an item was picked up, false otherwise
     */
    public boolean pickup() {
        // TODO: Check item is on same square
        // Pickup item in holding place
        // onPickup(): Princess game wins
        Integer[] coords = getTileCoordinates();
        System.out.println("Not implemented yet");
        return false;
    }

    /**
     * Drops the item at the entities item index.
     * The item is removed from the holding and placed on the map.
     */
    public void drop() {
        try {
            Item droppedItem = holding.remove(itemIndex);
            droppedItem.setPosition(getX(), getY());
            droppedItem.onDrop();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No item to drop");
        }
    }

    /**
     * Drops every item in the entity's holding.
     */
    public void dropAll() {
        for (int i = 0; i < holding.size(); i++) {
            switchItem(i);
            drop();
        }
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

    /**
     * Converts normal coordinates to coordinates of the tiles.
     *
     * @return the coordinates of the tile the entity is on
     */
    public Integer[] getTileCoordinates() {
        return new Integer[]{(int) getX() / tileWidth, (int) getY() / tileHeight};
    }

    public boolean isDead() {
        return isDead;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }


}
