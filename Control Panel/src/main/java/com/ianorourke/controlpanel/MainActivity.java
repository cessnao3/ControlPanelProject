package com.ianorourke.controlpanel;

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

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: FIX Window for GRIDS

        //TODO: Density Independent Pixels for Rectangles
        //TODO: Number of Rects based on Aspect Ratio
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Grid.gridPointList.size() == 0) {
            Point screenSize = new Point();
            getWindowManager().getDefaultDisplay().getSize(screenSize);

            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 3; x++) {
                    GridObject point = new GridObject(screenSize.x / 6 + screenSize.x * x / 3, screenSize.y / 8 + screenSize.y * y / 4);

                    Grid.gridPointList.add(point);
                }
            }
        }
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
                RectangleView rect = Grid.createRectangle(this);

                if (rect != null) addContentView(rect, new ViewGroup.LayoutParams(rect.getSize(), rect.getSize()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
