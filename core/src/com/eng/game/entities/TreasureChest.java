package com.eng.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.eng.game.items.Key;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;

public class TreasureChest extends Entity {
    private final Alliance keyAlliance;
    private final String description;

    public TreasureChest(BackgroundTiledMap tiledMap, ActorTable actorTable, String description) {
        super(tiledMap, actorTable, new Texture("img/treasure-chest.png"), 100, 3);
        actorTable.addActor(this);
        this.description = description;
        this.keyAlliance = new Alliance(toString(), this);
        setChestTexture("img/treasure-chest.png");
    }

    public void setChestTexture(String imgPath) {
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal(imgPath));
        Pixmap pixmapNew = new Pixmap(50, 50, pixmapOriginal.getFormat());
        pixmapNew.drawPixmap(pixmapOriginal,
                0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(),
                0, 0, pixmapNew.getWidth(), pixmapNew.getHeight()
        );
        Texture texture = new Texture(pixmapNew);
        pixmapOriginal.dispose();
        pixmapNew.dispose();
        super.setTexture(texture);
    }

    /**
     * If key is allied with the chest, the chest is opened.
     *
     * @param key the key to check
     * @return true if the chest is opened, false otherwise
     */
    @Override
    public boolean open(Key key) {
        if (keyAlliance.isAlly(key)) {
            System.out.println("You have opened the chest");
            setChestTexture("img/treasure-chest-open.png");
            dropAll();
            return true;
        }
        System.out.println("You need a key to open this chest");
        return false;
    }

    public void addKey(Key key) {
        keyAlliance.addAlly(key);
    }

    public Key generateKey() {
        Key key = new Key("Key", description, map, actorTable);
        addKey(key);
        return key;
    }

    @Override
    public String toString() {
        return "Treasure Chest: " + description;
    }
}
