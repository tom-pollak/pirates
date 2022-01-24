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

    public boolean isCellBlocked(float x, float y) {
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(i);
            TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));

            if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked"))
                return true;
        }
        return false;
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