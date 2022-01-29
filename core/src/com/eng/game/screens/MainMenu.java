package com.eng.game.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.eng.game.PirateGame;

/**
 * Creates the main menu
 * Is the opening menu and gets returned to after the win/loss screen
 */

public class MainMenu implements Screen{

    private final Texture background;
    private final Texture button;

    private Viewport viewport;
    private Camera camera;

    PirateGame game;

    public MainMenu(PirateGame game){
        this.game = game;
        // Gets texture paths and sets the camera
        background = new Texture("img/Background.PNG");
        button = new Texture("img/pngfind.com-start-button-png-3164705.png");

        camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        // Draws the background and the button
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background,0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.draw(button, viewport.getWorldWidth() /2 - 50, viewport.getWorldHeight() /2 - 50, 100, 100);

        // Gets the mouse location for the user to press the button
        if ((Gdx.input.getX() <= viewport.getScreenWidth() /2 + 75) && (Gdx.input.getX() >= viewport.getScreenWidth() /2 - 75) &&
                (Gdx.input.getY() <= viewport.getScreenHeight() / 2 + 75) && (Gdx.input.getY() >= viewport.getScreenHeight() / 2 - 75)&&
                (Gdx.input.isTouched())){
                this.dispose();
                game.setScreen(new Play(game));
        }
        game.batch.end();

    }

    @Override
    public void resize(int width, int height){
        // updates camera when the user changes screen size
        viewport.setCamera(camera);
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // Stop using textures when the screen changes
        background.dispose();
        button.dispose();
    }
}
