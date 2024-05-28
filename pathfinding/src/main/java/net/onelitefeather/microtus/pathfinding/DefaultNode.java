package net.onelitefeather.microtus.pathfinding;

public class DefaultNode implements Node {
    private final double x,y,z;

    private double gCost = Double.MAX_VALUE;
    private double hCost = Double.MAX_VALUE;
    private Node parent;

    public DefaultNode(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public Node parent() {
        return this.parent;
    }

    @Override
    public void parent(Node parent) {
        this.parent = parent;
    }

    @Override
    public double gcost() {
        return this.gCost;
    }

    @Override
    public void gcost(double gcost) {
        this.gCost = gcost;
    }

    @Override
    public double hcost() {
        return this.hCost;
    }

    @Override
    public void hcost(double hcost) {
        this.hCost = hcost;
    }
}
