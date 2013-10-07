package com.ianorourke.controlpanel.ShapeObjects;

import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout.RectangleView;

import android.content.Context;
import android.graphics.Point;
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

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float scale = getResources().getDisplayMetrics().density;

        GridController.rectSize = (int) (100.0 * scale + 0.5f);
        Log.v("cp", new Integer(GridController.rectSize).toString());

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

        Log.v("cp", new Integer(GridController.gridPointList.size()).toString());
    }

    public RectangleLayout createRect() {
        if (GridController.currentRect >= GridController.gridPointList.size() - 1) return null;

        RectangleLayout rectLayout = new RectangleLayout(getContext(), GridController.rectSize);

        Log.v("cp", new Integer(GridController.currentRect).toString());

        GridController.addRectangle(rectLayout);

        return rectLayout;
    }
}
