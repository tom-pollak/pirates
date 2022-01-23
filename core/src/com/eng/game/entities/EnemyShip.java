package com.eng.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayers;
import com.eng.game.items.Cannon;

public class EnemyShip extends Ship {
    public EnemyShip(Sprite sprite, MapLayers layers) {
        super(sprite, layers, 100, 3);
        pickup(new Cannon(10, 6, 2));
    }

    // Movement

}
