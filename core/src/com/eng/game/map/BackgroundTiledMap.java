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
import com.eng.game.actor.GameActor;
import com.sun.tools.javac.util.Pair;

import java.util.Objects;


/**
 * Loads renders and calculates collisions for a tiled map.
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
            if (!Objects.equals(map.getLayers().get(i).getName(), "collisionBoxes")) {
                TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(i);
                TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);
                if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked"))
                    return true;
            }
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
        return new Pair<>((int) (x / getTileWidth()), (int) (y / getTileHeight()));
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

    /**
     * Checks whether the two givin polygons intersect.
     *
     * @param hitbox   the first polygon
     * @param collider the second polygon
     * @return true if the two polygons intersect, false otherwise
     */
    public boolean isCollision(Polygon hitbox, Polygon collider) {
        return Intersector.intersectPolygons(
                new FloatArray(hitbox.getTransformedVertices()),
                new FloatArray(collider.getTransformedVertices()));
    }

    /**
     * Calculates if the ship has collided with land on the x or y axis.
     *
     * @param actor the ship to check
     * @param oldX  the old x coordinate of the ship
     * @param oldY  the old y coordinate of the ship
     * @return A boolean pair containing the x and y axis collision
     */
    public Pair<Boolean, Boolean> getMapCollisions(GameActor actor, float oldX, float oldY) {
        boolean collidedX = false;
        boolean collidedY = false;

        if (actor.getX() < 0 || actor.getRight() > map.getProperties().get("width", Integer.class) * getTileWidth()) {
            collidedX = true;
        }
        if (actor.getY() < 0 || actor.getTop() > map.getProperties().get("height", Integer.class) * getTileHeight()) {
            collidedY = true;
        }


        MapLayer collisionObjectLayer = map.getLayers().get("collisionBoxes");
        MapObjects objects = collisionObjectLayer.getObjects();
        Polygon hitbox = actor.getHitbox();

        for (PolygonMapObject colliders : objects.getByType(PolygonMapObject.class)) {
            Polygon collider = colliders.getPolygon();
            if (isCollision(hitbox, collider)) {
                hitbox.setPosition(actor.getX(), oldY);
                if (isCollision(hitbox, collider)) collidedX = true;

                hitbox.setPosition(oldX, actor.getY());
                if (isCollision(hitbox, collider)) collidedY = true;
            }
        }
        return new Pair<>(collidedX, collidedY);
    }
}