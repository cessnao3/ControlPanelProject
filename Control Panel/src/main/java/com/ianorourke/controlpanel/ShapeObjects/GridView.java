package com.ianorourke.controlpanel.ShapeObjects;

import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout.RectangleView;

import android.content.Context;
import android.graphics.Point;
import android.view.View;

import android.util.Log;

public class GridView extends View {
    public GridView(Context context) {
        super(context);

        this.setPadding(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float scale = getResources().getDisplayMetrics().density;

        GridController.rectSize = (int) (100.0 * scale + 0.5f);

        if (GridController.gridPointList.size() == 0) {
            Point screenSize = new Point(this.getWidth(), this.getHeight());

            int screenX = screenSize.x;
            int screenY = screenSize.y;

            int numX = (screenSize.x - GridController.rectSize / 4) / GridController.rectSize;
            int numY = (screenSize.y - GridController.rectSize / 4) / GridController.rectSize;

            for (int y = 0; y < numY; y++) {
                for (int x = 0; x < numX; x++) {
                    GridObject point = new GridObject(screenSize.x / (2 * numX) + screenSize.x * x / numX, screenSize.y / (2 * numY) + screenSize.y * y / numY);

                    GridController.gridPointList.add(point);
                }
            }
        }
    }
}
