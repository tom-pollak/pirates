package com.eng.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.eng.game.items.Item;

import java.util.ArrayList;

public abstract class Entity extends Sprite {
    private final ArrayList<Item> holding;
    private final int holdingCapacity;
    public int maxHealth;
    public int health;
    public Alliance alliance = Alliance.NEUTRAL;
    public boolean isDead;
    public int coins = 0;

    Entity(int maxHealth, int holdingCapacity, Sprite spriteImgPath) {
        super(spriteImgPath);
        this.holding = new ArrayList<>(holdingCapacity);
        this.holdingCapacity = holdingCapacity;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    Entity(int maxHealth, int holdingCapacity) {
        super();
        this.holding = new ArrayList<>(holdingCapacity);
        this.holdingCapacity = holdingCapacity;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public ArrayList<Item> getHolding() {
        return holding;
    }

    public boolean addItem(Item item) {
        if (holding.size() == holdingCapacity) {
            return false;
        }
        holding.add(item);
        return true;
    }

    public boolean pickup(Item item) {
        // Check item is on same square
        return addItem(item);
    }

    public boolean drop(Item item) {
        if (!holding.contains(item)) return false;
        holding.remove(item);
        return true;
    }

    public void damage(int damage) {
        health -= damage;
        if (health <= 0) onDeath();
    }

    public void heal(int heal) {
        health += heal;
        if (health > maxHealth) health = maxHealth;
    }

    public boolean onDeath() {
        isDead = true;
        // Trigger some winning animation if you win
        return false;
    }
}
