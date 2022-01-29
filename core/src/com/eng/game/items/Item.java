package com.eng.game.items;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.eng.game.logic.Alliance;

public class Item extends Actor {
    private final String name;
    private final String description;
    public Alliance alliance = Alliance.NEUTRAL;

    public Item(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public void use(int tileX, int tileY) {
        System.out.println(name + "cannot be used");
    }

    /**
     * Called when the item is picked up
     * By default sets actor's texture invisible
     */
    public void onPickup() {
        // Win if home base picks up princess
        System.out.println("You picked up " + name);
    }

    /**
     * Called when the item is dropped
     * By default sets actor's texture visible
     */
    public void onDrop() {
        // Texture appears
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
}
