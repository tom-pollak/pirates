package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.eng.game.actor.GameActor;
import com.eng.game.items.Cannon;
import com.eng.game.items.Coin;
import com.eng.game.items.Item;
import com.eng.game.items.Key;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.logic.Pair;
import com.eng.game.map.BackgroundTiledMap;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Main actor class for all non-inanimate objects in the game.
 */
public abstract class Entity extends GameActor {
    private final ArrayList<Item> holding;
    private final int holdingCapacity;
    public int maxHealth;
    public float health;
    public int coins = 0;
    Integer movementRange = null;
    private Cannon weapon = null;
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
        for (Item item : holding) {
            item.setPosition(getX(), getY());
            item.setOrigin(getOriginX(), getOriginY());
        }
        if (getWeapon() != null) {
            getWeapon().setPosition(getX(), getY());
            getWeapon().setOrigin(getOriginX(), getOriginY());
        }
    }

    public String getName() {
        return this.toString();
    }

    public abstract String toString();

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.setWidth(texture.getWidth());
        this.setHeight(texture.getHeight());
        this.setBounds(getX(), getY(), getWidth(), getHeight());
        this.setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
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

    public void addItem(Item item, int index) {
        item.onPickup(getAlliance());
        if (!(item instanceof Coin)) holding.add(index, item);
    }

    public void addItem(Item item) {
        item.onPickup(getAlliance());
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
            System.out.println("No item to pick up");
            return false;
        }
        if (getHeldItem() != null && !(item instanceof Coin)) {
            drop();
        }
        System.out.println("Picked up " + item);
        addItem(item, itemIndex);
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
        try {
            Item droppedItem = holding.remove(itemIndex);
            Integer x;
            Integer y;
            System.out.println(getX() + " " + getY());
            System.out.println(getOriginX() + " " + getOriginY());
            Pair<Integer, Integer> emptyTile = droppedItem.findEmptyTile(getOriginX(), getOriginY());
            if (emptyTile == null) {
                System.out.println("Entity.drop() - No empty tile found");
                return false;
            }
            x = emptyTile.fst;
            y = emptyTile.snd;
            System.out.println("Entity.drop() - Dropping item at " + x + ", " + y);
            droppedItem.setPosition(x, y);
            droppedItem.onDrop();
            System.out.println(this + " dropped " + droppedItem);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No item to drop");
            return false;
        }
        return true;
    }

    /**
     * Drops every item in the entity's holding.
     */
    public boolean dropAll() {
        boolean droppedAll = true;
        while (holding.size() > 0) {
            droppedAll = drop();
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
        if (index >= 0 && index < holdingCapacity) {
            System.out.println("Entity.switchItem() - switched to item " + getHeldItem());
            itemIndex = index;
        } else {
            System.out.println("Entity.switchItem() - index out of bounds");
        }
    }

    public void useItem() {
        Item item = getHeldItem();
        if (item != null) {
            item.use(actorTable.getCollidingEntities(this));
        } else {
            System.out.println("No item to use");
        }
    }

    public void useItem(float x, float y) {
        Item item = getHeldItem();
        if (item != null) {
            item.use(x, y);
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
    public void damage(float damage) {
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
        if (!(this instanceof CannonBall))
            System.out.println(this + " was destroyed!");
        alliance.removeAlly(this);
        dropAll();
        remove();
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
        if (getWeapon() == null) return 0;
        return getWeapon().getRange();
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

    @Override
    public void setAlliance(Alliance newAlliance) {
        HashSet<GameActor> oldAllianceMembers = (HashSet<GameActor>) alliance.getAllies().clone();
        oldAllianceMembers.remove(this);
        super.setAlliance(newAlliance);
        for (Item item : this.getHolding()) {
            item.setAlliance(newAlliance);
        }
        if (this instanceof College) {
            super.setAlliance(alliance);
            for (GameActor actor : oldAllianceMembers) {
                actor.setAlliance(newAlliance);
            }
        }
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public Cannon getWeapon() {
        return weapon;
    }

    public void setWeapon(Cannon weapon) {
        weapon.onPickup(getAlliance());
        this.weapon = weapon;
    }
}
