package com.eng.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;

public class Coin extends Item {
    private final int amount;


    public Coin(BackgroundTiledMap map, ActorTable actorTable, int amount) {
        super("Coin", "A coin worth " + amount, new Texture("img/coin.png"), map, actorTable);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public void onPickup(Alliance alliance) {
        if (alliance.isAlly(actorTable.getPlayer())) actorTable.getPlayer().addCoins(amount);
        super.onPickup(alliance);
    }
}
