package com.eng.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.logic.ActorTable;
import com.eng.game.map.BackgroundTiledMap;

public class Key extends Item {
    public Key(String name, String description, BackgroundTiledMap map, ActorTable actorTable) {
        super(name, description, new Texture("img/key.png"), map, actorTable);
    }

    /**
     * Calls the method open for the entity on the same tile as the key
     *
     * @param tileX the x coordinate of the tile
     * @param tileY the y coordinate of the tile
     */
    @Override
    public void use(int tileX, int tileY) {
        super.use(tileX, tileY);
        // TODO: implement use
    }
}
