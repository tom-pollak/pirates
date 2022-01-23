package com.eng.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

public class College extends Entity {

    public College(String name, int health, int holdingCapacity) {
        super(health, holdingCapacity);
        Alliance alliance = new Alliance(name);
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float delta) {
        generateCoins(delta);

    }

    private void generateCoins(float delta) {
        float cointMultiplier = 0.5F;
        if (delta > 0) {
            coins += (delta * cointMultiplier);

        }


    }
}
