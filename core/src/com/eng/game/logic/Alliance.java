package com.eng.game.logic;

import com.eng.game.entities.Entity;
import com.eng.game.entities.Ship;
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
    }

    public void addAlly(Item item) {
        alliedItems.add(item);
        item.alliance = this;
    }

    // Make isEnemy which is isAlly \cup NEUTRAL?

    public ArrayList<Ship> getShips() {
        ArrayList<Ship> ships = new ArrayList<>();
        for (Entity entity : alliedEntities) {
            if (entity instanceof Ship) {
                ships.add((Ship) entity);
            }
        }
        return ships;
    }

    public ArrayList<Item> getAlliedItems() {
        return alliedItems;
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
