package com.eng.game.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.FloatArray;
import com.eng.game.entities.Ship;
import com.sun.tools.javac.util.Pair;

import java.util.Objects;


public class BackgroundTiledMap extends Actor {

    public final TiledMap map;
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
            if (!Objects.equals(map.getLayers().get(i).getName(), "collisionBoxes")) {
                TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(i);
                TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
                if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked"))
                    return true;
            }
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

    public boolean isCollided(Polygon hitbox, Polygon collider) {
        return Intersector.intersectPolygons(
                new FloatArray(hitbox.getTransformedVertices()),
                new FloatArray(collider.getTransformedVertices()));
    }


    public Pair<Boolean, Boolean> getCollisions(Ship ship, float oldX, float oldY) {
        boolean collidedX = false;
        boolean collidedY = false;
        MapLayer collisionObjectLayer = map.getLayers().get("collisionBoxes");
        MapObjects objects = collisionObjectLayer.getObjects();
        Polygon hitbox = new Polygon();
        hitbox.setVertices(new float[]{
                0, 0,
                ship.getWidth(), 0,
                0, ship.getHeight(),
                ship.getWidth(), ship.getHeight()
        });
        hitbox.setPosition(ship.getX(), ship.getY());

        for (PolygonMapObject colliders : objects.getByType(PolygonMapObject.class)) {
            Polygon collider = colliders.getPolygon();
            if (isCollided(hitbox, collider)) {
                hitbox.setPosition(ship.getX(), oldY);
                if (isCollided(hitbox, collider)) collidedX = true;

                hitbox.setPosition(oldX, ship.getY());
                if (isCollided(hitbox, collider)) collidedY = true;

            }

        }
        return new Pair<>(collidedX, collidedY);

    }

}