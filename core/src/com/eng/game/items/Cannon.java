package com.eng.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eng.game.entities.CannonBall;
import com.eng.game.logic.ActorTable;
import com.eng.game.map.BackgroundTiledMap;

public class Cannon extends Item {
    private final float damage;
    private final int range;
    private final int projectileSpeed;
    private final float fireRate;
    private float timeSinceLastFire;


    public Cannon(int damage, int range, float fireRate, int projectileSpeed, BackgroundTiledMap map, ActorTable actorTable) {
        super("Cannon", "damage: " + damage + ", range: " + range + ", rate of fire: " + fireRate, new Texture("img/cannon.png"), map, actorTable);
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.timeSinceLastFire = fireRate;
        this.projectileSpeed = projectileSpeed;
    }

    @Override
    public void use(float x, float y) {
        Vector2 origin = new Vector2(getOriginX(), getOriginY());
        Vector2 dest = new Vector2(x, y);
        Vector2 cannonBallVector = dest.sub(origin).nor().setLength(projectileSpeed);
        fire(cannonBallVector);
    }

    @Override
    public void act(float delta) {
        timeSinceLastFire += delta;
    }

    public void fire(Vector2 cannonBallVector) {
        if (timeSinceLastFire >= fireRate) {
            System.out.println("Firing cannon");
            CannonBall cannonBall = new CannonBall(map, actorTable, cannonBallVector, damage, range * map.getTileWidth(), new Vector2(getOriginX(), getOriginY()), alliance);
            actorTable.addActor(cannonBall);
            timeSinceLastFire = 0;
        }
    }

    public float getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public float getFireRate() {
        return fireRate;
    }
}
