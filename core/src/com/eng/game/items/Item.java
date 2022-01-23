package com.eng.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item extends Sprite {
    private final String name;
    private final String description;

    Item(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }
}
