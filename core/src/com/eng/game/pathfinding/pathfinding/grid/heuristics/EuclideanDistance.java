package com.eng.game.pathfinding.pathfinding.grid.heuristics;

import com.eng.game.pathfinding.pathfinding.Heuristic;
import com.eng.game.pathfinding.pathfinding.NavigationNode;
import com.eng.game.pathfinding.pathfinding.grid.GridCell;

public class EuclideanDistance implements Heuristic {

    @Override
    public float calculate(NavigationNode from, NavigationNode to) {
        GridCell c1 = (GridCell) from, c2 = (GridCell) to;

        return calculate(c2.x - c1.x, c2.y - c1.y);
    }


    public float calculate(float deltaX, float deltaY) {
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

}
