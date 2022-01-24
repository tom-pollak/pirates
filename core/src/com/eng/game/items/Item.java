package com.eng.game.items;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Item extends Actor {
    private final String name;
    private final String description;

    public Item(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public void use() {
        System.out.println(name + "cannot be used");
    }

    public void onPickup() {
        System.out.println("You picked up " + name);
    }

    public void onDrop() {
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
