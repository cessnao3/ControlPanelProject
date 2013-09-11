package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.ShapeObjects.*;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.*;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private int currentRect = 0;

    private List<GridObject> gridPointList = new ArrayList<GridObject>();
    private List<RectangleView> rectangleList = new ArrayList<RectangleView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                GridObject point = new GridObject(screenSize.x / 6 + screenSize.x * x / 3, screenSize.y / 8 + screenSize.y * y / 4);

                gridPointList.add(point);
            }
        }
    }

    private void createRectangle() {
        RectangleView rect = new RectangleView(this, 300);

        rect.setX(0);
        rect.setY(0);

        currentRect++;
        rect.color = currentRect;

        rectangleList.add(rect);
        addContentView(rect, new ViewGroup.LayoutParams(rect.getSize(), rect.getSize()));

        alignObject(rect);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_align:
                for (int i = 0; i < gridPointList.size(); i++) {
                    //gridPointList.get(i).setOccupied(false);
                }

                for (int i = 0; i < rectangleList.size(); i++) {
                    alignObject(rectangleList.get(i));
                }

                return true;
            case R.id.menu_new:
                createRectangle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void alignObject(RectangleView rect) {
        Integer bestInt = null;
        Float bestDistance = null;

        for (int i = 0; i < gridPointList.size(); i++) {
            GridObject point = gridPointList.get(i);

            if (point.getObject() != null) {
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

            for (int i = 0; i < gridPointList.size(); i++) {
                if (newGridInt != i && gridPointList.get(i).getObject().equals(rect)) {
                    gridPointList.get(i).setObject(null);
                }
            }

            GridObject point = gridPointList.get(newGridInt);

            rect.setCenter(point.getPoint());
            point.setObject(rect);
        }
    }

    private float getDistance(PointF p1, PointF p2) {
        double x1 = (double) p1.x;
        double y1 = (double) p1.y;
        double x2 = (double) p2.x;
        double y2 = (double) p2.y;

        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
