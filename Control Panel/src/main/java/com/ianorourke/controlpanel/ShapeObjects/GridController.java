package com.ianorourke.controlpanel.ShapeObjects;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class GridController {
    public static List<GridObject> gridPointList = new ArrayList<GridObject>();

    public static int rectSize = 0;

    public static boolean isEditing = false;

    public static void addRectangle(RectangleLayout rect) {
        if (rect == null) return;

        boolean addedRect = false;

        for (int i = 0; i < gridPointList.size(); i++) {
            GridObject point = gridPointList.get(i);

            if (!point.hasObject()) {
                point.setObject(rect);

                addedRect = true;
                break;
            }
        }

        if (!addedRect) rect.removeSelf();

        resetAllObjects();
    }

    public static void addRectangle(RectangleLayout rect, int pos) {
        if (rect == null) return;

        if (pos < gridPointList.size() && pos >= 0) {
            GridObject point = gridPointList.get(pos);
            if (!point.hasObject()) point.setObject(rect);
        } else rect.removeSelf();

        resetAllObjects();
    }

    public static void alignObject(RectangleLayout rect) {
        if (rect == null) return;

        Integer bestInt = null;
        Float bestDistance = null;

        for (int i = 0; i < gridPointList.size(); i++) {
            GridObject point = gridPointList.get(i);

            if (!point.hasObject() || point.hasObject(rect)) {
                float currentDistance = getDistance(rect.getCenter(), point.getPoint());

                if (bestInt != null) {
                    if (bestDistance > currentDistance) {
                        bestInt = Integer.valueOf(i);
                        bestDistance = Float.valueOf(currentDistance);
                    }
                } else {
                    bestInt = Integer.valueOf(i);
                    bestDistance = Float.valueOf(currentDistance);
                }
            }
        }

        if (bestInt != null) {
            int newGridInt = bestInt.intValue();

            deleteRect(rect);
            gridPointList.get(newGridInt).setObject(rect);
        }
    }

    public static void updateRects() {
        for (GridObject gridObject : gridPointList) {
            //GridObject gridObject = gridPointList.get(i);

            RectangleLayout rect = gridObject.getObject();
            if (rect == null) continue;

            rect.updateRectDisplay();
        }
    }

    public static void deleteRect(RectangleLayout rect) {
        for (int i = 0; i < gridPointList.size(); i++) {
            GridObject point = gridPointList.get(i);

            if (point.hasObject(rect)) {
                point.setObject(null);
            }
        }
    }

    public static void deleteAllRects() {
        for (int i = 0; i < gridPointList.size(); i++) {
            GridObject point = gridPointList.get(i);

            if (point.hasObject()) point.getObject().removeSelf();
        }
    }

    public static void resetAllObjects() {
        for (int i = 0; i < gridPointList.size(); i++) {
            gridPointList.get(i).realignObject();
        }
    }

    public static void clearGridPoints() {
        deleteAllRects();

        gridPointList.clear();
    }

    public static int getNumRects() {
        int rects = 0;

        for (int i = 0; i < gridPointList.size(); i++) {
            if (gridPointList.get(i).hasObject()) rects++;
        }

        return rects;
    }

    private static float getDistance(PointF p1, PointF p2) {
        double x1 = (double) p1.x;
        double y1 = (double) p1.y;
        double x2 = (double) p2.x;
        double y2 = (double) p2.y;

        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}