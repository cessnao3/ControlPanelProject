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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Point screenSize = new Point();
            getWindowManager().getDefaultDisplay().getSize(screenSize);

            for (int y = 0; y < 3; y++) {
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
            case R.id.menu_align:
                for (int i = 0; i < Grid.gridPointList.size(); i++) {
                    Grid.gridPointList.get(i).setObject(null);
                }

                for (int i = 0; i < Grid.rectangleList.size(); i++) {
                    Grid.alignObject(Grid.rectangleList.get(i));
                }

                return true;
            case R.id.menu_new:
                RectangleView rect = Grid.createRectangle(this);

                if (rect != null) addContentView(rect, new ViewGroup.LayoutParams(rect.getSize(), rect.getSize()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
