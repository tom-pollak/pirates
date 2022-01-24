package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.eng.game.items.Item;
import com.eng.game.items.Key;

import java.util.ArrayList;

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
    private int itemIndex = 0;


    Entity(Texture texture, int maxHealth, int holdingCapacity) {
        this.texture = texture;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        if (holdingCapacity > 9) holdingCapacity = 9;
        this.holding = new ArrayList<>(holdingCapacity);
        this.holdingCapacity = holdingCapacity;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public abstract String toString();

    public Texture getTexture() {
        return texture;
    }

    public ArrayList<Item> getHolding() {
        return holding;
    }

    public boolean addItem(Item item) {
        if (holding.size() == holdingCapacity) {
            return false;
        }
        holding.add(item);
        // Use return value to check if item was added
        return true;
    }

    public boolean pickup() {
        // TODO: Check item is on same square
        // Get Item on same sqare
//        return addItem(item);
        // onPickup()
        return false;
    }

    public void drop() {
        try {
            Item droppedItem = holding.remove(itemIndex);
            droppedItem.setPosition(getX(), getY());
            droppedItem.onDrop();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No item to drop");
        }
    }

    public void dropAll() {
        for (int i = 0; i < holding.size(); i++) {
            switchItem(i);
            drop();
        }
    }

    public void switchItem(int index) {
        itemIndex = index;
    }

    public void damage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }

    public void heal(int heal) {
        health += heal;
        if (health > maxHealth) health = maxHealth;
    }

    public void die() {
        dropAll();
        isDead = true;
    }

    public boolean open(Key key) {
        System.out.println("Cannot be opened");
        return false;
    }
}
