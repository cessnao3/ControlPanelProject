package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.Orbiter.OrbiterConnect;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;

import android.util.Log;

public class MainActivity extends Activity {

    private GridView mainView;

    private String labelText = "Hello, World!";

    //private GridLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: Move points and move rects to new points

        Log.v("cp", ((savedInstanceState == null) ? "State Null" : "State True") + ", " + ((this.mainView == null) ? "View Null" : "View True"));

        if (savedInstanceState == null || this.mainView == null) {
            GridController.clearGridPoints();

            this.mainView = new GridView(this);
            GridController.clearGridPoints();

            //TODO: Work with GridLayout -> Cleaner Solution

            //this.mainLayout = new GridLayout(this);
            //setContentView(this.mainLayout.getLayout());

            setContentView(this.mainView);
        }

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
                //this.mainLayout.createRect();

                //TODO: Move this Functionality to GridLayout

                RectangleLayout rectLayout = this.mainView.createRect();

                if (rectLayout != null) {
                    RectangleView rect = rectLayout.getRectangleView();

                    addContentView(rectLayout.layout, new ViewGroup.LayoutParams(rectLayout.getSize(), rectLayout.getSize()));
                }

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
                    RectangleLayout rectangleLayout = this.mainView.createRect();

                    if (rectangleLayout != null) {
                        RectangleView rect = rectangleLayout.getRectangleView();

                        addContentView(rectangleLayout.layout, new ViewGroup.LayoutParams(rectangleLayout.getSize(), rectangleLayout.getSize()));
                    }
                }

                OrbiterConnect orbConnect = new OrbiterConnect();
                orbConnect.connectToOrbiter();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}