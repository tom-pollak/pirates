package com.eng.game.items;

public class Cannon extends Item {
    private final float damage;
    private final int range;
    private final float fireRate;


    public Cannon(int damage, int range, int fireRate) {
        super("Cannon", "A cannon that can shoot");
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
    }

    @Override
    public void use() {
        fire();
    }

    public void fire() {
        System.out.println("Firing cannon");
    }
}
