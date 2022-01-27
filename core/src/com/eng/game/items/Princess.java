package com.eng.game.items;

public class Princess extends Item {
    public Princess() {
        super("Princess", "A beautiful princess");
    }

    /**
     * If the princess is picked up by the players home college, the player wins the game.
     */
    // TODO: If home base picks up princess, you win
    @Override
    public void onPickup() {
        System.out.println("You picked up the princess");
        super.onPickup();
    }
}
