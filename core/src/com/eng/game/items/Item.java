package com.eng.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;

public class Item extends Actor {
    private final String name;
    private final String description;
    private final BackgroundTiledMap map;
    private final Texture texture;
    public Alliance alliance = Alliance.NEUTRAL;
    public boolean isHeld = false;

    public Item(String name, String description, Texture texture, BackgroundTiledMap map) {
        super();
        this.name = name;
        this.description = description;
        this.map = map;
        this.texture = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isHeld) {
            batch.draw(texture, getX(), getY());
        }
        super.draw(batch, parentAlpha);
    }

    public void use(int tileX, int tileY) {
        System.out.println(name + " cannot be used");
    }

    public void use() {
        System.out.println(name + " cannot be used");
    }

    /**
     * Called when the item is picked up
     * By default sets actor's texture invisible
     */
    public void onPickup() {
        // Win if home base picks up princess
        isHeld = true;
        System.out.println("You picked up " + name);
    }

    /**
     * Called when the item is dropped
     * By default sets actor's texture visible
     */
    public void onDrop() {
        // Texture appears
        isHeld = false;
        System.out.println("Dropped " + name);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return name;
    }

    public int getTileX() {
        return (int) getX() / map.getTileWidth();
    }

    public int getTileY() {
        return (int) getY() / map.getTileHeight();
    }
}
