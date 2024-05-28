package net.onelitefeather.microtus.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class DefaultPathfindingAlgorithm implements PathfindingAlgorithm {
    @Override
    public List<Node> findPath(Node startNode, Node endNode) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(Node::fcost));

        HashSet<Node> closedList = new HashSet<>();
        startNode.gcost(0);
        startNode.hcost(getHeuristic(startNode, endNode));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            closedList.add(currentNode);

            if (currentNode.equals(endNode)) {
                return retracePath(startNode, endNode);
            }

            for (Node neighbor : getNeighbors(currentNode)) {
                if (closedList.contains(neighbor)) continue;

                double tentativeGCost = currentNode.gcost() + getDistance(currentNode, neighbor);
                if (tentativeGCost < neighbor.gcost() || !openList.contains(neighbor)) {
                    neighbor.gcost(tentativeGCost);
                    neighbor.hcost(getHeuristic(neighbor, endNode));
                    neighbor.parent(currentNode);

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
        return List.of();
    }

    /**
     * Retrieves the neighboring nodes of a given node.
     * This method considers the six main directions (up, down, left, right, front, back).
     *
     * @param node the node whose neighbors are to be retrieved.
     * @return the list of neighboring nodes.
     */
    @Override
    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        double[][] directions = {
                {1, 0, 0},  // Right
                {-1, 0, 0}, // Left
                {0, 1, 0},  // Up
                {0, -1, 0}, // Down
                {0, 0, 1},  // Front
                {0, 0, -1}  // Back
        };

        for (double[] direction : directions) {
            double newX = node.x() + direction[0];
            double newY = node.y() + direction[1];
            double newZ = node.z() + direction[2];

            neighbors.add(new DefaultNode(newX, newY, newZ));
        }

        return neighbors;
    }

    /**
     * Calculates the heuristic cost (H cost) from the given node to the target node.
     * The H cost is an estimate of the cost to reach the target node from the given node.
     *
     * @param a the start node.
     * @param b the end node.
     * @return the heuristic cost.
     */
    private double getHeuristic(Node a, Node b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y()) + Math.abs(a.z() - b.z());
    }

    /**
     * Calculates the distance between two nodes.
     * This distance is used to calculate the G cost.
     *
     * @param a the first node.
     * @param b the second node.
     * @return the distance between the nodes.
     */
    private double getDistance(Node a, Node b) {
        return Math.sqrt(Math.pow(a.x() - b.x(), 2) + Math.pow(a.y() - b.y(), 2) + Math.pow(a.z() - b.z(), 2));
    }

    /**
     * Retraces the path from the end node to the start node.
     * This method builds the path by following the parent nodes from the end node to the start node.
     *
     * @param startNode the start node.
     * @param endNode the end node.
     * @return the list of nodes representing the path.
     */
    private List<Node> retracePath(Node startNode, Node endNode) {
        List<Node> path = new ArrayList<>();
        Node currentNode = endNode;

        while (currentNode != startNode) {
            path.add(currentNode);
            currentNode = currentNode.parent();
        }
        Collections.reverse(path);
        return path;
    }

}
