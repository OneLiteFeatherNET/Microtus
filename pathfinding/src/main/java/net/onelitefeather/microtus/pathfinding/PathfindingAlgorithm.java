package net.onelitefeather.microtus.pathfinding;

import java.util.List;

public interface PathfindingAlgorithm {

    List<Node> findPath(Node start, Node end);

    default List<Node> findPath(double xStart, double yStart, double zStart, double xEnd, double yEnd, double zEnd) {
        return findPath(new DefaultNode(xStart, xStart, zStart),
                new DefaultNode(xEnd, yEnd, zEnd));
    }

    List<Node> getNeighbors(Node node);
}
