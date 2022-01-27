package com.eng.game.pathfinding.pathfinding.grid.finders;

import com.eng.game.pathfinding.pathfinding.finders.AStarFinder;
import com.eng.game.pathfinding.pathfinding.grid.NavigationGridGraph;
import com.eng.game.pathfinding.pathfinding.grid.NavigationGridGraphNode;

import java.util.List;

/**
 * A helper class to which lets you find a path based on coordinates rather than nodes on {@link NavigationGridGraph}'s.
 *
 * @param <T> any class that inherits from {@link NavigationGridGraphNode}
 * @author Xavier Guzman
 */
public class AStarGridFinder<T extends NavigationGridGraphNode> extends AStarFinder<T> {

    public AStarGridFinder(Class<T> clazz) {
        this(clazz, new GridFinderOptions());
    }

    public AStarGridFinder(Class<T> clazz, GridFinderOptions options) {
        super(clazz, options);
    }

    /**
     * Find and return the path. The resulting collection should never be modified, copy the values instead.
     *
     * @return The path from [startX, startY](exclusive) to [endX, endY]
     */
    public List<T> findPath(int startX, int startY, int endX, int endY, NavigationGridGraph<T> grid) {
        return findPath(grid.getCell(startX, startY), grid.getCell(endX, endY), grid);
    }

}
