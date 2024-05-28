package net.onelitefeather.microtus.pathfinding;


/**
 * This interface represents a node used in pathfinding algorithms like A*.
 * It provides methods to get and set the node's coordinates, costs, and parent.
 */
public interface Node extends Comparable<Node> {

    /**
     * Returns the X coordinate of the node.
     *
     * @return the X coordinate.
     */
    double x();

    /**
     * Returns the Y coordinate of the node.
     *
     * @return the Y coordinate.
     */
    double y();
    /**
     * Returns the Z coordinate of the node.
     *
     * @return the Z coordinate.
     */
    double z();

    /**
     * Returns the parent node.
     * The parent node is the node from which the current node was reached.
     *
     * @return the parent node.
     */
    Node parent();

    void parent(Node patent);

    /**
     * Returns the G cost of the node.
     * G cost is the movement cost from the start node to the current node.
     *
     * @return the G cost.
     */
    double gcost();

    void gcost(double gcost);
    /**
     * Returns the H cost of the node.
     * H cost is the estimated cost from the current node to the end node (heuristic).
     *
     * @return the H cost.
     */
    double hcost();

    void hcost(double hcost);

    /**
     * Returns the F cost of the node.
     * F cost is the sum of G cost and H cost.
     *
     * @return the F cost.
     */
    default double fcost() {
        return gcost() + hcost();
    }

    @Override
    default int compareTo(final Node o) {
        return Double.compare(this.fcost(), o.fcost());
    }
}
