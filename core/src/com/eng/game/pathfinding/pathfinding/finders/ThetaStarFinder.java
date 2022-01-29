package com.eng.game.pathfinding.pathfinding.finders;

import com.eng.game.pathfinding.pathfinding.*;
import com.eng.game.pathfinding.pathfinding.grid.GridCell;
import com.eng.game.pathfinding.pathfinding.grid.NavigationGridGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A finder which will use theta star algorithm on a grid (any angle path finding).
 * It also lets you find a path based on coordinates rather than nodes on {@link NavigationGridGraph}'s.
 *
 * @param <T> any class that inherits from {@link GridCell}
 * @author Xavier Guzman
 */
public abstract class ThetaStarFinder<T extends NavigationNode> implements PathFinder<T> {

    private final PathFinderOptions defaultOptions;
    public int jobId;
    BHeap<T> openList;

    public ThetaStarFinder(Class<T> clazz, PathFinderOptions opt) {
        this.defaultOptions = opt;
        openList = new BHeap<>(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                if (o1 == null || o2 == null) {
                    if (o1 == o2)
                        return 0;
                    if (o1 == null)
                        return -1;
                    else
                        return 1;

                }
                return (int) (o1.getF() - o2.getF());
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<T> findPath(T startNode, T endNode, NavigationGraph<T> graph) {

        Util.validateNotNull(startNode, "Start node cannot be null");
        Util.validateNotNull(endNode, "End node cannot be null");

        if (jobId == Integer.MAX_VALUE)
            jobId = 0;
        int job = ++jobId;

        T node, neighbor;
        List<T> neighbors = new ArrayList<>();
        float ng;

        Util.validateNotNull(startNode, "Start node cannot be null");
        Util.validateNotNull(endNode, "End node cannot be null");


        startNode.setG(0);
        startNode.setF(0);

        // push the start node into the open list
        openList.clear();
        openList.add(startNode);
        startNode.setParent(null);
        startNode.setOpenedOnJob(job, this.getClass());

        while (openList.size > 0) {

            // pop the position of node which has the minimum 'f' value.
            node = openList.pop();
            node.setClosedOnJob(job, this.getClass());


            // if reached the end position, construct the path and return it
            if (node == endNode) {
                return Util.backtrace(endNode);
            }

            // get neighbors of the current node
            neighbors.clear();
            neighbors.addAll(graph.getNeighbors(node, defaultOptions));
            for (T t : neighbors) {
                neighbor = t;

                if (neighbor.getClosedOnJob(this.getClass()) == job || !graph.isWalkable(neighbor)) {
                    continue;
                }

                T parent;

                if (graph.lineOfSight(node.getParent(), neighbor)) {
                    // get the distance between parent node and the neighbor and calculate the next g score
                    ng = node.getParent().getG() + graph.getMovementCost((T) node.getParent(), neighbor, defaultOptions);
                    parent = (T) node.getParent();
                } else {
                    // get the distance between current node and the neighbor and calculate the next g score
                    ng = node.getG() + graph.getMovementCost(node, neighbor, defaultOptions);
                    parent = node;
                }

                // check if the neighbor has not been inspected yet, or can be reached with smaller cost from the current node
                if (neighbor.getOpenedOnJob(this.getClass()) != job || ng < neighbor.getG()) {
                    float prevf = neighbor.getF();
                    neighbor.setG(ng);

                    neighbor.setH(defaultOptions.heuristic.calculate(neighbor, endNode));
                    neighbor.setF(neighbor.getG() + neighbor.getH());
                    neighbor.setParent(parent);

                    if (neighbor.getOpenedOnJob(this.getClass()) != job) {
                        openList.add(neighbor);
                        neighbor.setOpenedOnJob(job, this.getClass());
                    } else {
                        // the neighbor can be reached with smaller cost.
                        // Since its f value has been updated, we have to update its position in the open list
                        openList.updateNode(neighbor, neighbor.getF() - prevf);
                    }
                }
            }
        }

        // fail to find the path
        return null;
    }
}