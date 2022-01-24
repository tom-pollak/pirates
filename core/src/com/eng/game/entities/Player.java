package com.eng.game.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.eng.game.map.BackgroundTiledMap;

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
            }
            return true;
        }

        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            switch (keycode) {
                case Input.Keys.A:
                case Input.Keys.D:
                    velocity.x = 0;
                    break;

                case Input.Keys.W:
                case Input.Keys.S:
                    velocity.y = 0;
                    break;
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
            }
            if (Character.isDigit(character) && (int) character >= 1 && (int) character <= 9) {
                switchItem((int) character - 1);
            }
            return true;
        }
    };

    public Player(BackgroundTiledMap backgroundTiledMap) {
        super(new Texture("img/player.png"), 100, 3, backgroundTiledMap);

    }

    @Override
    public String toString() {
        return "Player";
    }


}
