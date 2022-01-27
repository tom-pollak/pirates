package com.eng.game.items;

public class Key extends Item {
    public Key(String name, String description) {
        super(name, description);
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
