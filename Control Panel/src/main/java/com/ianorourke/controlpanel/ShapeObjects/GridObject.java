package com.ianorourke.controlpanel.ShapeObjects;

import android.graphics.PointF;

public class GridObject {
    //TODO: Fix Occupied

    private PointF gridPoint;
    private boolean occupied = false;

    public GridObject(float x, float y) {
        this.gridPoint = new PointF(x, y);
    }

    public void setPoint(PointF newPoint) {
        this.gridPoint = newPoint;
    }

    public PointF getPoint() {
        return this.gridPoint;
    }

    public void setOccupied(boolean value) {
        this.occupied = value;
    }

    public boolean getOccupied() {
        return this.occupied;
    }
}
