package com.eng.game.logic;

import com.eng.game.entities.Entity;
import com.eng.game.items.Item;

import java.util.ArrayList;

public class Alliance {
    public static Alliance NEUTRAL;
    private final String name;
    private ArrayList<Entity> alliedEntities;
    private ArrayList<Item> alliedItems;

    public Alliance(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Alliance{" +
                "name='" + name + '\'' +
                '}';
    }


    public void addAlly(Entity entity) {
        alliedEntities.add(entity);
    }

    public void addAlly(Item item) {
        alliedItems.add(item);
    }

    public boolean isAlly(Entity entity) {
        return alliedEntities.contains(entity);
    }

    public boolean isAlly(Item item) {
        return alliedItems.contains(item);
    }

    public String getName() {
        return name;
    }


}
