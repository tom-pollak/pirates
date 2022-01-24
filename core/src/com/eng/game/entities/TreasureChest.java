package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.items.Key;

public class TreasureChest extends Entity {
    private final Alliance keyAlliance;
    private final String description;

    public TreasureChest(Alliance keyAlliance, String description) {
        super(new Texture("img/ship.png"), 100, 3);
        this.description = description;
        this.keyAlliance = new Alliance(toString());
    }

    public boolean open(Key key) {
        if (keyAlliance.isAlly(key)) {
            System.out.println("You have opened the chest");
            die();
            return true;
        }
        System.out.println("You need a key to open this chest");
        return false;
    }

    public void addKey(Key key) {
        keyAlliance.addAlly(key);
    }

    public Key generateKey() {
        Key key = new Key("Key", description);
        addKey(key);
        return key;
    }

    @Override
    public String toString() {
        return "Treasure Chest: " + description;
    }
}
