package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.logic.Pathfinding;
import com.eng.game.map.BackgroundTiledMap;
import com.eng.game.screens.Play;
import com.sun.tools.javac.util.Pair;

/**
 * Leader & base entity for each team
 * Creates the college and its alliance
 * Spawn point for ships and generates coins
 */
public class College extends Entity {
    private final Integer range;
    private final Pair<Integer, Integer> spawnPoint;
    private final Integer maxShips;
    private final Pathfinding pathfinding;

    public College(BackgroundTiledMap tiledMap, ActorTable actorTable, Pathfinding pathfinding, Texture shipTexture, String name, int health, int holdingCapacity, Integer range, Pair<Integer, Integer> spawnPoint, Integer maxShips) {
        super(tiledMap, actorTable, new Texture("img/college.gif"), health, holdingCapacity);
        setAlliance(new Alliance(name, this, shipTexture));
        this.range = range;
        this.spawnPoint = spawnPoint;
        this.maxShips = maxShips;
        this.pathfinding = pathfinding;
        actorTable.addActor(this);
    }

    @Override
    public void act(float delta) {
        generateCoins(delta);
        generateShips(delta);
        // Check pickup area
    }

    /**
     * Generates coins for the college over time.
     *
     * @param delta time since last update
     */
    private void generateCoins(float delta) {
        float coinMultiplier = 0.5F;
        if (delta > 0) {
            coins += (delta * coinMultiplier);

        }
    }

    private void generateShips(float delta) {
        if (getAlliance().getShips().size() < maxShips && Math.random() < 0.01) {
            EnemyShip enemyShip = new EnemyShip(map, actorTable, pathfinding, alliance.getShipTexture());
            enemyShip.setPosition(spawnPoint.fst, spawnPoint.snd);
            enemyShip.setAlliance(getAlliance());

        }
    }

    public Integer getMovementRange() {
        return range;
    }

    @Override
    public void die() {
        Play.collegeDeath = getName();
        setAlliance(Alliance.NEUTRAL);
        Vector2 position = new Vector2(getX(), getY());
        setPosition(spawnPoint.fst, spawnPoint.snd);
        dropAll();
        setPosition(position.x, position.y);
        // Destroyed college texture
        setTexture(new Texture("img/college.gif"));
        System.out.println(getName() + "college destroyed!");

    }

    @Override
    public String toString() {
        return alliance.getName() + " college";
    }
}
