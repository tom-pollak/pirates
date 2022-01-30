package com.eng.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.logic.ActorTable;
import com.eng.game.map.BackgroundTiledMap;

public class Princess extends Item {
    public Princess(BackgroundTiledMap map, ActorTable actorTable) {
        super("Princess", "A beautiful princess", new Texture("img/princess.png"), map, actorTable);

    }

    /**
     * If the princess is picked up by the players home college, the player wins the game.
     */
    // TODO: If home base picks up princess, you win
    @Override
    public void onPickup() {
        System.out.println("You picked up the princess");
        super.onPickup();
    }
}
