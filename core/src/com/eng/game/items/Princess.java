package com.eng.game.items;

public class Princess extends Item {
    public Princess() {
        super("Princess", "A beautiful princess");
    }

    @Override
    public void onPickup() {
        System.out.println("You picked up the princess");
    }
}
