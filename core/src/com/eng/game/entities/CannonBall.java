package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.map.BackgroundTiledMap;

import java.util.ArrayList;

public class CannonBall extends Entity {

    private final Vector2 velocity;
    private final Vector2 originalPosition;
    private final float damage;
    private final int range;

    public CannonBall(BackgroundTiledMap map, ActorTable actorTable, Vector2 velocity, float damage, int range, Vector2 position, Alliance alliance) {
        super(map, actorTable, new Texture("img/cannonball.gif"), 1, 0);
        this.velocity = velocity;
        this.damage = damage;
        this.range = range;
        this.originalPosition = position;
        this.setPosition(position.x, position.y);
        this.setAlliance(alliance);
        System.out.println("Cannon ball alliance" + alliance);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);
        float distFromOrigin = new Vector2(getX(), getY()).dst(originalPosition);
        ArrayList<Entity> collidingEntities = actorTable.getCollidingEntities(this);
        for (Entity entity : collidingEntities) {
            if (!alliance.isAlly(entity)) {
                System.out.println("Cannon ball alliance" + alliance + " colliding entity alliance" + entity.getAlliance());
                System.out.println("Cannon ball hit " + entity + " dealing " + damage + " damage");
                entity.damage(damage);
                die();
            }
        }
        if (distFromOrigin > range) {
            die();
        }
    }

    @Override
    public String toString() {
        return "CannonBall{" +
                "velocity=" + velocity +
                '}';
    }
}
