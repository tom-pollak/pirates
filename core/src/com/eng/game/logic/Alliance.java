package com.eng.game.logic;

import com.eng.game.entities.Entity;
import com.eng.game.items.Item;

import java.util.ArrayList;

public class Alliance {
    public static Alliance NEUTRAL = new Alliance("Neutral", null);
    private final String name;
    private final ArrayList<Entity> alliedEntities = new ArrayList<>();
    private final ArrayList<Item> alliedItems = new ArrayList<>();
    private Entity leader;

    public Alliance(String name, Entity leader) {
        this.name = name;
        this.leader = leader;
    }

    @Override
    public String toString() {
        // TODO: Not sure about this string?
        return "Alliance{" +
                "name='" + name + '\'' +
                '}';
    }

    public Entity getLeader() {
        return leader;
    }

    public void setLeader(Entity leader) {
        this.leader = leader;
    }

    public void addAlly(Entity entity) {
        alliedEntities.add(entity);
        entity.alliance = this;
        System.out.println(entity.getName() + " has joined the " + name + " alliance!");
    }

    public void addAlly(Item item) {
        alliedItems.add(item);
        item.alliance = this;
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
