package com.ianorourke.controlpanel;

import android.app.ActionBar;
import com.ianorourke.controlpanel.ShapeObjects.*;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.PointF;
import android.text.AndroidCharacter;
import android.view.*;
import android.util.DisplayMetrics;

import java.util.List;
import java.util.ArrayList;

import android.graphics.Rect;

import android.util.Log;

public class MainActivity extends Activity {

    private float scale;

    GridView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainView = new GridView(this);
        setContentView(this.mainView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //TODO: FIX Window for GRIDS

        //TODO: Density Independent Pixels for Rectangles
        //TODO: Number of Rects based on Aspect Ratio
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.scale = getResources().getDisplayMetrics().density;

        //rectSize = (300.0 * this.scale + 0.5f);
        Grid.rectSize = (int) (50.0 * this.scale + 0.5f);
        Log.v("cp", new Integer(Grid.rectSize).toString());

        if (Grid.gridPointList.size() == 0) {
            Point screenSize = new Point();
            getWindowManager().getDefaultDisplay().getSize(screenSize);

            //Point screenSize = new Point(getWindow().getDecorView().getWidth(), getWindow().getDecorView().getHeight());

            //TODO: Fix Window Sizing

            int numX = (screenSize.x - Grid.rectSize) / Grid.rectSize;
            int numY = (screenSize.y - Grid.rectSize) / Grid.rectSize;

            //TODO: Remove - 2 from numY

            for (int y = 0; y < numY - 1; y++) {
                for (int x = 0; x < numX; x++) {
                    GridObject point = new GridObject(screenSize.x / (2 * numX) + screenSize.x * x / numX, screenSize.y / (2 * numY) + screenSize.y * y / numY);

                    Grid.gridPointList.add(point);
                }
            }
        }

        Log.v("cp", new Integer(Grid.gridPointList.size()).toString());
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
            case R.id.menu_new:

                this.mainView.createRectangle();
                return true;
                //RectangleView rect = Grid.createRectangle(this);

                //if (rect != null) addContentView(rect, new ViewGroup.LayoutParams(rect.getSize(), rect.getSize()));
                //return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
