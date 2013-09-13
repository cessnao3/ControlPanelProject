package com.ianorourke.controlpanel.ShapeObjects;

import android.graphics.PointF;

public class GridObject {
    //TODO: Fix Occupied

    private PointF gridPoint;
    private RectangleView currentObject = null;

    public GridObject(float x, float y) {
        this.gridPoint = new PointF(x, y);
    }

    public void setPoint(PointF newPoint) {
        this.gridPoint = newPoint;
    }

    public PointF getPoint() {
        return this.gridPoint;
    }

    public void setObject(RectangleView newObject) {
        this.currentObject = newObject;
    }

    public void refreshObject() {
        if (this.currentObject != null) {
            this.currentObject.setCenter(this.getPoint());
        }
    }

    public RectangleView getObject() {
        return this.currentObject;
    }

    public boolean hasObject() {
        if (currentObject != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasObject(RectangleView rect) {
        if (currentObject != null) {
            if (currentObject.equals(rect)) return false;
            else return true;
        } else {
            return false;
        }
    }
}
