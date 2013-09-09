package com.ianorourke.controlpanel;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;

import android.graphics.Point;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private List<GridObject> gridPointList = new ArrayList<GridObject>();

    private List<RectangleView> rectangleList = new ArrayList<RectangleView>();

    private float defaultSize = 300;
    private ViewGroup.LayoutParams rectangleParams = new ViewGroup.LayoutParams((int) defaultSize, (int) defaultSize);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                GridObject point = new GridObject(screenSize.x / 6 + screenSize.x * x / 3, screenSize.y / 8 + screenSize.y * y / 4);
                gridPointList.add(point);
            }
        }

        for (int i = 0; i < 2; i++) {
            RectangleView rect = new RectangleView(this, defaultSize, RectangleView.RectColors.RED);

            rectangleList.add(rect);
            addContentView(rect, rectangleParams);
        }

        this.alignRectangles();
    }

    private void alignRectangles() {
        for (int i = 0; i < rectangleList.size(); i++) {
            int bestPoint = -1;
            double bestDistance = -1;

            for (int j = 0; j < gridPointList.size(); j++) {
                if (bestPoint != -1) {
                    double currentDistance = getDistance(rectangleList.get(i).getCenter(), gridPointList.get(j).getPoint());

                    if (bestDistance > currentDistance && !gridPointList.get(j).getOccupied()) {
                        bestDistance = currentDistance;
                        bestPoint = j;
                    }
                } else {
                    if (!gridPointList.get(j).getOccupied()) {
                        bestPoint = j;
                    }
                }
            }

            if (bestPoint != -1) {
                rectangleList.get(i).setCenter(gridPointList.get(bestPoint).getPoint());
                gridPointList.get(bestPoint).setOccupied(true);
            }
        }
    }

    private double getDistance(PointFloat p1, PointFloat p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }
}
