package com.ianorourke.controlpanel;

public class GridObject {
    private PointFloat gridPoint;
    private boolean occupied = false;

    public GridObject(float x, float y) {
        this.gridPoint = new PointFloat(x, y);
    }

    public double getX() {
        return this.gridPoint.x;
    }

    public double getY() {
        return this.gridPoint.y;
    }

    public PointFloat getPoint() {
        return this.gridPoint;
    }

    public void setOccupied(boolean value) {
        this.occupied = value;
    }

    public boolean getOccupied() {
        return this.occupied;
    }
}
