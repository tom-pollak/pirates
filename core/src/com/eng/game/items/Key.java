package com.eng.game.items;

public class Key extends Item {
    public Key(String name, String description) {
        super(name, description);
    }

    @Override
    public void use() {
        super.use();
        // Use on entitiy on current tile
    }
}
