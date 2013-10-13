package com.ianorourke.controlpanel.ShapeObjects;

import android.graphics.PointF;

public class GridObject {
    private PointF gridPoint;
    private RectangleLayout currentObject = null;

    public GridObject(float x, float y) {
        this.gridPoint = new PointF(x, y);
    }

    public void setPoint(PointF newPoint) {
        this.gridPoint = newPoint;
    }

    public PointF getPoint() {
        return this.gridPoint;
    }

    public void setObject(RectangleLayout newObject) {
        this.currentObject = newObject;
        this.realignObject();
    }

    public void realignObject() {
        if (this.currentObject != null) {
            this.currentObject.setCenter(this.getPoint());
        }
    }

    public RectangleLayout getObject() {
        return this.currentObject;
    }

    public boolean hasObject() {
        return (currentObject != null) ? true : false;
    }

    public boolean hasObject(RectangleLayout rect) {
        if (currentObject != null && currentObject.equals(rect)) return true;
        else return false;
    }
}
