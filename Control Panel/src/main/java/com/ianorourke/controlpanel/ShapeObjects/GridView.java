package com.ianorourke.controlpanel.ShapeObjects;

import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout.RectangleView;

import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GridView extends View {
    public GridView(Context context) {
        super(context);

        this.setPadding(0, 0, 0, 0);
    }

    public RectangleLayout createRect() {
        //if (Grid.currentRect >= Grid.gridPointList.size() - 1) return null;

        RectangleLayout rectLayout = new RectangleLayout(getContext(), Grid.rectSize);

        Grid.currentRect++;

        Log.v("cp", new Integer(Grid.currentRect).toString());

        Grid.addRectangle(rectLayout);

        return rectLayout;
    }

    public static class Grid {
        public static List<GridObject> gridPointList = new ArrayList<GridObject>();
        //public static List<RectangleView> rectangleList = new ArrayList<RectangleView>();

        public static int rectSize = 300;

        public static int currentRect = 0;

        public static void addRectangle(RectangleLayout rect) {
            alignObject(rect);
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

        public static void deleteRect(RectangleLayout rect) {
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

        private static float getDistance(PointF p1, PointF p2) {
            double x1 = (double) p1.x;
            double y1 = (double) p1.y;
            double x2 = (double) p2.x;
            double y2 = (double) p2.y;

            return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        }
    }
}
