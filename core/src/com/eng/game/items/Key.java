package com.eng.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.entities.Entity;
import com.eng.game.entities.TreasureChest;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;

import java.util.ArrayList;


public class Key extends Item {
    public Key(String name, String description, BackgroundTiledMap map, ActorTable actorTable) {
        super(name, description, new Texture("img/key.png"), map, actorTable);
    }

    /**
     * Calls the method open for the entity on the same tile as the key
     *
     * @param entities the entities currently interctable with the key
     */

    @Override
    public void use(ArrayList<Entity> entities) {
        // TODO: implement use
        for (Entity entity : entities) {
            if (entity instanceof TreasureChest) {
                entity.open(this);
            }
        }
    }

    @Override
    public void onPickup(Alliance alliance) {
        isHeld = true;
    }
}
