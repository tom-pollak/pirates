package com.eng.game.logic;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.actor.GameActor;
import com.eng.game.entities.Entity;
import com.eng.game.entities.Ship;

import java.util.ArrayList;

public class Alliance {
    public static Alliance NEUTRAL = new Alliance("Neutral", null, new Texture("img/ship.png"));
    private final String name;
    private final ArrayList<GameActor> alliedActors = new ArrayList<>();
    private final Texture shipTexture;
    private Entity leader;

    public Alliance(String name, Entity leader, Texture shipTexture) {
        this.name = name;
        this.leader = leader;
        this.shipTexture = shipTexture;
    }

    public Texture getShipTexture() {
        return shipTexture;
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


    public void removeAlly(GameActor actor) {
        alliedActors.remove(actor);
    }

    public void addAlly(GameActor actor) {
        alliedActors.add(actor);
    }

    // Make isEnemy which is isAlly \cup NEUTRAL?

    public ArrayList<Ship> getShips() {
        ArrayList<Ship> ships = new ArrayList<>();
        for (GameActor gameActor : alliedActors) {
            if (gameActor instanceof Ship) {
                ships.add((Ship) gameActor);
            }
        }
        return ships;
    }

    public ArrayList<GameActor> getAllies() {
        return alliedActors;
    }

    public boolean isAlly(GameActor actor) {
        return this.equals(actor.getAlliance());
    }

    public String getName() {
        return name;
    }
}
