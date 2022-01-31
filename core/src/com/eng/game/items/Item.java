package com.eng.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.eng.game.actor.GameActor;
import com.eng.game.entities.Entity;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;
import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;


public class Item extends GameActor {
    private final String name;
    private final String description;
    public Alliance alliance = Alliance.NEUTRAL;
    public boolean isHeld = false;

    public Item(String name, String description, Texture texture, BackgroundTiledMap map, ActorTable actorTable) {
        super(map, actorTable, texture);
        actorTable.addActor(this);
        this.name = name;
        this.description = description;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isHeld) {
            batch.draw(texture, getX(), getY());
        }
        super.draw(batch, parentAlpha);
    }

    public void use(ArrayList<Entity> entities) {
        System.out.println(name + " cannot be used");
    }

    public void use(Integer x, Integer y) {
        System.out.println(name + " cannot be used");
    }

    /**
     * Called when the item is picked up
     * By default sets actor's texture invisible
     */
    public void onPickup() {
        // Win if home base picks up princess
        isHeld = true;
        System.out.println("You picked up " + name);
    }

    /**
     * Called when the item is dropped
     * By default sets actor's texture visible
     */
    public void onDrop() {
        // Texture appears
        isHeld = false;
        System.out.println("Dropped " + name);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return name;
    }

    /**
     * Finds coordinate that the item can be placed without overlapping with another item or the tile is not blocked.
     *
     * @param startX x coordinate of the tile to check around
     * @param startY y coordinate of the tile to check around
     * @return a pair of coordinates
     */
    public Pair<Integer, Integer> findEmptyTile(float startX, float startY) {
        for (int x = (int) ((int) startX - 2 * getWidth()); x <= startX + 2 * getWidth(); x++) {
            for (int y = (int) ((int) startY - 2 * getHeight()); y <= startY + 2 * getHeight(); y++) {
                if (actorTable.willItemCollide(this, x, y) && !map.isTileBlocked(x, y)) {
                    return new Pair<>(x, y);
                }
            }
        }
        return null;
    }
}
