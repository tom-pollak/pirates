package com.eng.game.items;

public class Princess extends Item {
    public Princess() {
        super("Princess", "A beautiful princess");
    }

    @Override
    public void onPickup() {
        // TODO: If home base picks up princess, you win
        System.out.println("You picked up the princess");
    }
}
