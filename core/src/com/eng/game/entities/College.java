package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;

/**
 * Leader & base entity for each team
 * Creates the college and its alliance
 * Spawn point for ships and generates coins
 */
public class College extends Entity {
    private final Alliance alliance;
    private final Integer range;

    public College(BackgroundTiledMap tiledMap, ActorTable actorTable, String name, int health, int holdingCapacity, Integer range) {
        super(tiledMap, actorTable, new Texture("img/college.gif"), health, holdingCapacity);
        this.alliance = new Alliance(name, this);
        this.alliance.addAlly(this);
        this.alliance.setLeader(this);
        this.range = range;
        actorTable.addActor(this);
    }

    public void update(float delta) {
        generateCoins(delta);
    }

    /**
     * Generates coins for the college over time.
     *
     * @param delta time since last update
     */
    private void generateCoins(float delta) {
        float coinMultiplier = 0.5F;
        if (delta > 0) {
            coins += (delta * coinMultiplier);

        }
    }

    public Integer getMovementRange() {
        return range;
    }

    @Override
    public String toString() {
        return alliance.getName() + " college";
    }
}
