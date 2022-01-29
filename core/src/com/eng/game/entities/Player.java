package com.eng.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.eng.game.logic.ActorTable;
import com.eng.game.map.BackgroundTiledMap;

/**
 * Player class, extends a ship but in the players control
 */
public class Player extends Ship {
    public final InputListener input = new InputListener() {
        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            switch (keycode) {
                case Input.Keys.W:
                    velocity.y = speed;
                    break;
                case Input.Keys.S:
                    velocity.y = -speed;
                    break;
                case Input.Keys.A:
                    velocity.x = -speed;
                    break;
                case Input.Keys.D:
                    velocity.x = speed;
                    break;
                default:
                    return false;
            }
            return true;
        }

        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            switch (keycode) {
                case Input.Keys.A:
                    /* D is pressed and A is released, reverse the direction */
                    if (!Gdx.input.isKeyPressed(Input.Keys.D)) velocity.x = 0;
                    else velocity.x = speed;
                    break;
                case Input.Keys.D:
                    if (!Gdx.input.isKeyPressed(Input.Keys.A)) velocity.x = 0;
                    else velocity.x = -speed;
                    break;
                case Input.Keys.W:
                    if (!Gdx.input.isKeyPressed(Input.Keys.S)) velocity.y = 0;
                    else velocity.y = -speed;
                    break;
                case Input.Keys.S:
                    if (!Gdx.input.isKeyPressed(Input.Keys.W)) velocity.y = 0;
                    else velocity.y = speed;
                    break;
                default:
                    return false;
            }
            return true;
        }

        @Override
        public boolean keyTyped(InputEvent event, char character) {
            switch (character) {
                case 'e':
                    pickup();
                    break;
                case 'f':
                    drop();
                    break;
                case ' ':
                    useItem();
                    break;

            }
            /* Select holding item */

            if (Character.isDigit(character) && (int) character >= 1 && (int) character <= 9) {
                switchItem((int) character - 1);
            }
            return true;
        }
    };

    public Player(BackgroundTiledMap map, ActorTable actorTable) {
        super(map, actorTable, new Texture("img/player.png"), 100, 3, 100);
    }

    @Override
    public String toString() {
        return "Player";
    }


}
