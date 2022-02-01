package com.eng.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;

public class GameActor extends Actor {

    protected final BackgroundTiledMap map;
    protected final ActorTable actorTable;
    protected Texture texture;
    protected Alliance alliance = Alliance.NEUTRAL;

    public GameActor(BackgroundTiledMap map, ActorTable actorTable, Texture texture) {
        super();
        this.map = map;
        this.texture = texture;
        this.actorTable = actorTable;

        this.setWidth(texture.getWidth());
        this.setHeight(texture.getHeight());
        this.setBounds(0, 0, getWidth(), getHeight());
    }

    public static Texture getScaledTexture(String imgPath, int width, int height) {
        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal(imgPath));
        Pixmap pixmapNew = new Pixmap(width, height, pixmapOriginal.getFormat());
        pixmapNew.drawPixmap(pixmapOriginal,
                0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(),
                0, 0, pixmapNew.getWidth(), pixmapNew.getHeight()
        );
        Texture texture = new Texture(pixmapNew);
        pixmapOriginal.dispose();
        pixmapNew.dispose();
        return texture;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);

    }

    @Override
    public void setX(float x) {
        super.setX(x);
        this.setOriginX(getX() + getWidth() / 2);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        this.setOriginY(getY() + getHeight() / 2);
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

    public Alliance getAlliance() {
        return alliance;
    }

    public void setAlliance(Alliance newAlliance) {
        alliance.removeAlly(this);

        this.alliance = newAlliance;
        newAlliance.addAlly(this);
    }
}
