package com.eng.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.eng.game.items.Item;
import com.eng.game.logic.ActorTable;
import com.eng.game.map.BackgroundTiledMap;

/**
 * Player class, extends a ship but in the players control
 */
public class Player extends Ship {
    public final InputProcessor input = new InputProcessor() {

        private int x;
        private int y;
        private int pointer;
        private int button;

        @Override
        public boolean keyDown(int keycode) {
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
        public boolean keyUp(int keycode) {
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
        public boolean keyTyped(char character) {
            switch (character) {
                case 'e':
                    pickup();
                    break;
                case 'f':
                    drop();
                    break;
                case ' ':
                    useItem();
                    System.out.println(getX() + " " + getY());
                    break;
                case 'r':
                    System.out.println("Inventory:");
                    for (int i = 0; i < getHolding().size(); i++) {
                        Item item = getHolding().get(i);
                        System.out.println("\t(" + (i + 1) + ") " + item.getName() + ": " + item.getDescription());
                    }
                    break;

            }

            /* Select holding item */
            if (Character.isDigit(character)) {
                switchItem(Character.getNumericValue(character) - 1);
            }
            return true;

        }


        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }

        @Override
        public boolean touchDown(int x, int y, int pointer, int button) {
            Vector3 v = new Vector3(x, y, 0);
            try {
                Vector3 position = getStage().getCamera().unproject(v);
                if (button == Input.Buttons.LEFT) {
                    useItem(position.x, position.y);
                }
                return true;

            } catch (Exception e) {
                return false;
            }
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
