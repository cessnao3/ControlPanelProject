package com.ianorourke.controlpanel.ShapeObjects;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class GridController {
    public static List<GridObject> gridPointList = new ArrayList<GridObject>();
    //public static List<RectangleView> rectangleList = new ArrayList<RectangleView>();

    public static int rectSize = 300;

    public static int currentRect = 0;

    public static void addRectangle(RectangleLayout rect) {
        if (rect != null) {
            boolean addedRect = false;

            for (int i = 0; i < gridPointList.size(); i++) {
                GridObject point = gridPointList.get(i);

                if (!point.hasObject()) {
                    point.setObject(rect);
                    rect.setCenter(point.getPoint());
                    addedRect = true;
                    currentRect++;
                    break;
                }
            }

            if (!addedRect) {
                rect.removeSelf();
            }
        }
    }

    public static boolean alignObject(RectangleLayout rect) {
        if (rect == null) return false;

        Integer bestInt = null;
        Float bestDistance = null;

        GridObject[][] objects = new GridObject[3][4];

        for (int i = 0; i < gridPointList.size(); i++) {
            GridObject point = gridPointList.get(i);

            if (!point.hasObject(rect)) {
                float currentDistance = getDistance(rect.getCenter(), point.getPoint());

                if (bestInt != null) {
                    if (bestDistance > currentDistance) {
                        bestInt = new Integer(i);
                        bestDistance = new Float(currentDistance);
                    }
                } else {
                    bestInt = new Integer(i);
                    bestDistance = new Float(currentDistance);
                }
            }
        }

        if (bestInt != null) {
            int newGridInt = bestInt.intValue();

            deleteRect(rect);

            GridObject point = gridPointList.get(newGridInt);

            rect.setCenter(point.getPoint());
            point.setObject(rect);

            return true;
        } else {
            return false;
        }
    }

    public static void updateRects(String string) {
        for (int i = 0; i < gridPointList.size(); i++) {
            GridObject gridObject = gridPointList.get(i);

            RectangleLayout rect = gridObject.getObject();

            if (rect != null) {
                rect.setText(string);
            }
        }
    }

    public static void deleteRect(RectangleLayout rect) {
        for (int i = 0; i < gridPointList.size(); i++) {
            GridObject point = gridPointList.get(i);

            if (point.hasObject(rect)) {
                point.setObject(null);

                //TODO: Fix Multiple Deletions of Rects
                currentRect--;
            }
        }
    }

    public static void resetAllObjects() {
        for (int i = 0; i < gridPointList.size(); i++) {
            gridPointList.get(i).realignObject();
        }
    }

    public static void clearGridPoints() {
        for (int i = 0; i < gridPointList.size(); i++) {
            GridObject point = gridPointList.get(i);

            if (point.hasObject()) point.getObject().removeSelf();
        }

        gridPointList.clear();
        currentRect = 0;
    }

    private static float getDistance(PointF p1, PointF p2) {
        double x1 = (double) p1.x;
        double y1 = (double) p1.y;
        double x2 = (double) p2.x;
        double y2 = (double) p2.y;

        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
