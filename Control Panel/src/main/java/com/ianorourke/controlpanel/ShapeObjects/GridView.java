package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.View;

public class GridView extends View {
    public GridView(Context context) {
        super(context);

        this.setPadding(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float scale = getResources().getDisplayMetrics().density;

        GridController.rectSize = (int) (150.0 * scale + 0.5f);

        Point screenSize = new Point(this.getWidth(), this.getHeight());

        int numX = (int) Math.floor(screenSize.x / GridController.rectSize);
        int numY = (int) Math.floor(screenSize.y / GridController.rectSize);

        if (numX == 0 || numY == 0) return;

        if (GridController.gridPointList.size() == 0) {
            for (int y = 0; y < numY; y++) {
                for (int x = 0; x < numX; x++) {
                    GridObject point = new GridObject(screenSize.x / (2 * numX) + screenSize.x * x / numX, screenSize.y / (2 * numY) + screenSize.y * y / numY);

                    GridController.gridPointList.add(point);
                }
            }
        } else {
            while (GridController.gridPointList.size() > numX * numY) {
                int lastValue = GridController.gridPointList.size() - 1;

                GridObject point = GridController.gridPointList.get(lastValue);

                if (point != null && point.hasObject()) point.getObject().removeSelf();

                GridController.gridPointList.remove(lastValue);
            }

            for (int y = 0; y < numY; y++) {
                for (int x = 0; x < numX; x++) {
                    int i = y * numX + x;

                    PointF newPoint = new PointF(screenSize.x / (2 * numX) + screenSize.x * x / numX, screenSize.y / (2 * numY) + screenSize.y * y / numY);

                    if (GridController.gridPointList.size() > i) {
                        GridController.gridPointList.get(i).setPoint(newPoint);
                    } else {
                        GridObject point = new GridObject(newPoint.x, newPoint.y);

                        GridController.gridPointList.add(point);
                    }
                }
            }
        }

        GridController.resetAllObjects();
    }
}
