package com.eng.game.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.eng.game.entities.Ship;

import java.util.Objects;

import static com.badlogic.gdx.math.Intersector.intersectSegmentPolygon;


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
            if(!Objects.equals(map.getLayers().get(i).getName(), "collisionBoxes")){
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

    public boolean newCollidesX(Ship ship) {
        //Take the layer that has all collision objects from the map
        MapLayer collisionObjectLayer = (MapLayer) map.getLayers().get("collisionBoxes");
        MapObjects objects = collisionObjectLayer.getObjects();
        //offset makes the ships hitbox lines on each side smaller (in pixels)
        float offset = 3f;
        boolean collided = false;
        //CODE BELOW FOR RECTANGLES
        //rectangles on each side of ship for collision on X side
        Rectangle xCollisionRect1 = new Rectangle(ship.getX(), ship.getY()+offset, 1, ship.getHeight()-2*offset);
        Rectangle xCollisionRect2 = new Rectangle(ship.getX()+getWidth(), ship.getY()+offset, 1, ship.getHeight()-2*offset);
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            //check if these rectangles overlap (collide) with any collision object from the layer
            if (Intersector.overlaps(rectangle, xCollisionRect1) || Intersector.overlaps(rectangle, xCollisionRect2)) {
                collided = true;
            }
        }

        //CODE BELOW FOR POLYGONS
        // These 3 vectors are lines for the ship checking for  X intersections with the colliders (edges of map, islands etc.)
        // The formation looks like:
        // |      |
        // |      |
        // |------|
        // |      |
        // |      |
        Vector2 leftLineBottom = new Vector2(ship.getX(), ship.getY()+offset);
        Vector2 leftLineTop = new Vector2(ship.getX(), ship.getY()+ship.getHeight()-offset*2);
        Vector2 rightLineBottom = new Vector2(ship.getX()+ship.getWidth(), ship.getY()-offset);
        Vector2 rightLineTop = new Vector2(ship.getX()+ship.getWidth(), ship.getY()+ship.getHeight()-offset*2);
        Vector2 middleLineLeft = new Vector2(ship.getX(), ship.getY()+ship.getHeight()/2);
        Vector2 middleLineRight  = new Vector2(ship.getX()+ship.getWidth(), ship.getY()+ship.getHeight()/2);

        //
        for (PolygonMapObject colliders : objects.getByType(PolygonMapObject.class)){
            Polygon collider = colliders.getPolygon();
            //check if these lines overlap (collide) with any collision object from the layer
            if (intersectSegmentPolygon(leftLineBottom, leftLineTop, collider)
                    || intersectSegmentPolygon(rightLineBottom, rightLineTop , collider) ||
            intersectSegmentPolygon(middleLineLeft, middleLineRight, collider)) {
                collided = true;
            }
        }
        return collided;
    }

    public boolean newCollidesY(Ship ship) {
        //Take the layer that has all collision objects from the map
        MapLayer collisionObjectLayer = (MapLayer) map.getLayers().get("collisionBoxes");
        MapObjects objects = collisionObjectLayer.getObjects();
        //offset makes the ships hitbox lines on each side smaller (in pixels)
        float offset = 2f;
        boolean collided = false;

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            Rectangle xCollisionRect1 = new Rectangle(ship.getX()+offset, ship.getY(), ship.getWidth()-2*offset, 1);
            Rectangle xCollisionRect2 = new Rectangle(ship.getX()+offset, ship.getY()+getHeight(), ship.getWidth()-2*offset, 1);
            if (Intersector.overlaps(rectangle, xCollisionRect1) || Intersector.overlaps(rectangle, xCollisionRect2)) {
                collided = true;
                System.out.print("hi");
            }
        }
        //CODE BELOW FOR POLYGONS#
        // These 3 vectors are lines for the ship checking for Y intersections with the colliders (edges of map, islands etc.)
        // The formation looks like:
        //---------
        //    |
        //    |
        //    |
        //---------
        Vector2 bottomLineLeft = new Vector2(ship.getX()+offset, ship.getY());
        Vector2 bottomLineRight = new Vector2(ship.getX()+ship.getWidth()-offset*2, ship.getY());
        Vector2 topLineLeft = new Vector2(ship.getX()+offset, ship.getY()+ship.getHeight());
        Vector2 topLineRight = new Vector2(ship.getX()+ship.getWidth()-offset*2, ship.getY()+getHeight());
        Vector2 middleLineBottom = new Vector2(ship.getX()+ship.getWidth()/2, ship.getY());
        Vector2 middleLineTop = new Vector2(ship.getX()+ship.getWidth()/2, ship.getY()+ship.getHeight());
        for (PolygonMapObject colliders : objects.getByType(PolygonMapObject.class)){
            Polygon collider = colliders.getPolygon();
            //check if these lines overlap (collide) with any collision object from the layer
            if (intersectSegmentPolygon(bottomLineLeft, bottomLineRight, collider)
                    || intersectSegmentPolygon(topLineLeft, topLineRight, collider)
            || intersectSegmentPolygon(middleLineBottom, middleLineTop, collider)) {
                collided = true;
            }
        }
        return collided;

    }

}