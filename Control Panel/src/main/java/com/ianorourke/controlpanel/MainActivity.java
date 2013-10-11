package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.Orbiter.OrbiterConnect;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;

import android.util.Log;

public class MainActivity extends Activity {

    //private GridView mainView;

    private String labelText = "Hello, World!";

    private GridLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: Move points and move rects to new points
        //TODO: http://techblogon.com/android-screen-orientation-change-rotation-example/

        Log.v("cp", ((savedInstanceState == null) ? "State Null" : "State True") + ", " + ((this.mainLayout == null) ? "Layout Null" : "Layout True"));

        //if (savedInstanceState == null || this.mainView == null) {
        if (GridController.gridPointList.size() == 0) {

            this.mainLayout = new GridLayout(this);

            setContentView(this.mainLayout.getLayout());
        } else GridController.resetAllGridPoints();

        GridController.resetAllObjects();
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
                this.mainLayout.createRect();

                return true;
            case R.id.menu_changetext:
                if (labelText.equals("HI!")) {
                    labelText = "Hello, World!";
                } else {
                    labelText = "HI!";
                }

                GridController.updateRects(labelText);

                return true;
            case R.id.menu_connect:
                if (GridController.currentRect == 0) {
                    mainLayout.createRect();
                }

                OrbiterConnect orbConnect = new OrbiterConnect();
                orbConnect.connectToOrbiter();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}