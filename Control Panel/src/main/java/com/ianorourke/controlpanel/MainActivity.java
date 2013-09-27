package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.ShapeObjects.*;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.*;

import android.util.Log;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    private GridView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainView = new GridView(this);
        setContentView(this.mainView);
    }

    @Override
    public void onResume() {
        super.onResume();

        float scale = getResources().getDisplayMetrics().density;

        GridView.Grid.rectSize = (int) (100.0 * scale + 0.5f);
        Log.v("cp", new Integer(GridView.Grid.rectSize).toString());


        if (GridView.Grid.gridPointList.size() == 0) {
            Point screenSize = new Point(this.mainView.getWidth(), this.mainView.getHeight());

            //TODO: Size for this.mainView

            int screenX = screenSize.x;
            int screenY = screenSize.y;

            int numX = (screenSize.x - GridView.Grid.rectSize / 4) / GridView.Grid.rectSize;
            int numY = (screenSize.y - GridView.Grid.rectSize / 4) / GridView.Grid.rectSize;

            for (int y = 0; y < numY; y++) {
                for (int x = 0; x < numX; x++) {
                    GridObject point = new GridObject(screenSize.x / (2 * numX) + screenSize.x * x / numX, screenSize.y / (2 * numY) + screenSize.y * y / numY);

                    GridView.Grid.gridPointList.add(point);
                }
            }
        }

        Log.v("cp", new Integer(GridView.Grid.gridPointList.size()).toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new:
                RelativeLayout rect = this.mainView.createRectangle();

                if (rect != null) addContentView(rect, new ViewGroup.LayoutParams(GridView.Grid.rectSize, GridView.Grid.rectSize));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}