package com.ianorourke.controlpanel.ShapeObjects;

public class GridObject {
    //TODO: Fix Occupied

    private PointD gridPoint;
    private boolean occupied = false;

    public GridObject(float x, float y) {
        this.gridPoint = new PointD(x, y);
    }

    public void setPoint(PointD newPoint) {
        this.gridPoint = newPoint;
    }

    public PointD getPoint() {
        return this.gridPoint;
    }

    public void setOccupied(boolean value) {
        this.occupied = value;
    }

    public boolean getOccupied() {
        return this.occupied;
    }
}
