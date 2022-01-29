package com.eng.game.logic;

import com.eng.game.map.BackgroundTiledMap;

public class TileCoord {
    private final int tileX;
    private final int tileY;

    public TileCoord(int x, int y, BackgroundTiledMap map) {
        this.tileX = x / map.getTileWidth();
        this.tileY = y / map.getTileHeight();
    }
}
