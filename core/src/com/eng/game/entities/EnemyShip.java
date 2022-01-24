package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.items.Cannon;
import com.eng.game.map.BackgroundTiledMap;

public class EnemyShip extends Ship {
    public EnemyShip(BackgroundTiledMap backgroundTiledMap) {
        // TODO: add enemy ship texture based on alliance
        super(new Texture("img/ship.png"), 100, 3, backgroundTiledMap);
        addItem(new Cannon(10, 6, 2));
    }

    @Override
    public String toString() {
        return "Enemy ship";
    }

    // TODO: Add movement and pathfinding (AI)

}
