package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;

public class College extends Entity {
    private final Alliance alliance;

    public College(String name, int health, int holdingCapacity) {
        super(new Texture("img/ship.png"), health, holdingCapacity);
        this.alliance = new Alliance(name);
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

    @Override
    public String toString() {
        return alliance.getName() + " college";
    }
}
