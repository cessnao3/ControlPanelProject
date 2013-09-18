package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    public static List<GridObject> gridPointList = new ArrayList<GridObject>();
    //public static List<RectangleView> rectangleList = new ArrayList<RectangleView>();

    public static int currentRect = 0;

    public static RectangleView createRectangle(Context context) {
        if (currentRect >= 11) return null;

        RectangleView rect = new RectangleView(context, 300);

        rect.setX(0);
        rect.setY(0);

        currentRect++;
        rect.color = currentRect;

        //rectangleList.add(rect);

        alignObject(rect);

        return rect;
    }

    public static boolean alignObject(RectangleView rect) {
        Integer bestInt = null;
        Float bestDistance = null;

        GridObject[][] objects = new GridObject[3][4];

        if (rect == null) return false;

        for (int i = 0; i < gridPointList.size(); i++) {
            GridObject point = gridPointList.get(i);

            if (point.hasObject(rect) == false) {
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

    public static void deleteRect(RectangleView rect) {
        for (int i = 0; i < gridPointList.size(); i++) {
            if (gridPointList.get(i).hasObject()) {
                if (gridPointList.get(i).getObject().equals(rect)) gridPointList.get(i).setObject(null);
            }
        }
    }

    public static void resetAllObjects() {
        for (int i = 0; i < gridPointList.size(); i++) {
            gridPointList.get(i).realignObject();
        }
    }

    public static float getDistance(PointF p1, PointF p2) {
        double x1 = (double) p1.x;
        double y1 = (double) p1.y;
        double x2 = (double) p2.x;
        double y2 = (double) p2.y;

        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
