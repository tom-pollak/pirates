package com.eng.game.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sun.tools.javac.util.Pair;


/**
 * Loads and renders an orthogonal tiled map.
 */
public class BackgroundTiledMap extends Actor {

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    Stage stage;

    public BackgroundTiledMap(Stage stage) {
        map = new TmxMapLoader().load("maps/world.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 70, 50);
        camera.update();
        this.stage = stage;
        this.stage.getViewport().setCamera(camera);
    }

    public MapLayers getLayers() {
        return map.getLayers();
    }

    public int getTileWidth() {
        TiledMapTileLayer indexLayer = (TiledMapTileLayer) map.getLayers().get(0);
        return indexLayer.getTileWidth();
    }

    public int getTileHeight() {
        TiledMapTileLayer indexLayer = (TiledMapTileLayer) map.getLayers().get(0);
        return indexLayer.getTileHeight();
    }

    /**
     * Checks if the tile at the given coordinates is blocked.
     *
     * @param tileX: the x coordinate of the tile
     * @param tileY: the y coordinate of the tile
     * @return: true if the tile has the blocked property, false otherwise
     */
    public boolean isTileBlocked(int tileX, int tileY) {
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(i);
            TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);

            if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked"))
                return true;
        }
        return false;
    }


    /**
     * Returns the tile coordinates of the given world coordinates.
     *
     * @param x the x coordinate of the world
     * @param y the y coordinate of the world
     * @return a pair containing the tile x and tile y coordinates
     */
    public Pair<Integer, Integer> getTileCoords(float x, float y) {
        return new Pair<Integer, Integer>((int) (x / getTileWidth()), (int) (y / getTileHeight()));
    }

    public int getTileX(float x) {
        return (int) (x / getTileWidth());
    }

    public int getTileY(float y) {
        return (int) (y / getTileHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        render();
        super.draw(batch, parentAlpha);
    }

    public void render() {
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    public void dispose() {
        renderer.dispose();
        map.dispose();
    }
}