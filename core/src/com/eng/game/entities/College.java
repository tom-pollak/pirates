package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;

public class College extends Entity {
    private final Alliance alliance;
    private final Integer range;

    public College(BackgroundTiledMap tiledMap, String name, int health, int holdingCapacity, Integer range) {
        super(tiledMap, new Texture("img/ship.png"), health, holdingCapacity);
        this.alliance = new Alliance(name, this);
        this.alliance.addAlly(this);
        this.alliance.setLeader(this);
        this.range = range;
    }

    public void update(float delta) {
        generateCoins(delta);
    }

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
