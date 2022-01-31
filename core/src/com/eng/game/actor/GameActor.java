package com.eng.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.eng.game.logic.ActorTable;
import com.eng.game.map.BackgroundTiledMap;

public class GameActor extends Actor {

    protected final BackgroundTiledMap map;
    protected final Texture texture;
    protected final ActorTable actorTable;

    public GameActor(BackgroundTiledMap map, ActorTable actorTable, Texture texture) {
        super();
        this.map = map;
        this.texture = texture;
        this.actorTable = actorTable;

        this.setWidth(texture.getWidth());
        this.setHeight(texture.getHeight());
        this.setBounds(0, 0, texture.getWidth(), texture.getHeight());
        this.setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public Polygon getHitbox() {
        Polygon hitbox = new Polygon();
        hitbox.setVertices(new float[]{
                0, 0,
                getWidth(), 0,
                0, getHeight(),
                getWidth(), getHeight()
        });
        hitbox.setPosition(getX(), getY());
        return hitbox;
    }

    public int getTileX() {
        return (int) getX() / map.getTileWidth();
    }

    public int getTileY() {
        return (int) getY() / map.getTileHeight();
    }
}
