package com.eng.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.eng.game.logic.ActorTable;
import com.eng.game.logic.Alliance;
import com.eng.game.logic.Pathfinding;
import com.eng.game.map.BackgroundTiledMap;
import com.sun.tools.javac.util.Pair;

/**
 * Leader & base entity for each team
 * Creates the college and its alliance
 * Spawn point for ships and generates coins
 */
public class College extends Entity {
    private final Alliance alliance;
    private final Integer range;
    private final Pair<Integer, Integer> spawnPoint;
    private final Integer maxShips;
    private final Pathfinding pathfinding;
    private final Texture shipTexture;

    public College(BackgroundTiledMap tiledMap, ActorTable actorTable, Pathfinding pathfinding, Texture shipTexture, String name, int health, int holdingCapacity, Integer range, Pair<Integer, Integer> spawnPoint, Integer maxShips) {
        super(tiledMap, actorTable, new Texture("img/college.gif"), health, holdingCapacity);
        this.alliance = new Alliance(name, this);
        this.alliance.addAlly(this);
        this.alliance.setLeader(this);
        this.range = range;
        this.spawnPoint = spawnPoint;
        this.maxShips = maxShips;
        this.pathfinding = pathfinding;
        this.shipTexture = shipTexture;
        actorTable.addActor(this);
    }

    @Override
    public void act(float delta) {
        generateCoins(delta);
        generateShips(delta);
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
            EnemyShip enemyShip = new EnemyShip(map, actorTable, pathfinding, shipTexture);
            enemyShip.setPosition(spawnPoint.fst, spawnPoint.snd);
            getAlliance().addAlly(enemyShip);

        }
    }

    public Integer getMovementRange() {
        return range;
    }

    @Override
    public String toString() {
        return alliance.getName() + " college";
    }
}
