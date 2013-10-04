package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.Orbiter.OrbiterConnect;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;

public class MainActivity extends Activity {

    private GridView mainView;

    private String labelText = "Hello, World!";

    //private GridLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainView = new GridView(this);
        GridController.clearGridPoints();

        //TODO: Work with GridLayout -> Cleaner Solution

        //this.mainLayout = new GridLayout(this);
        //setContentView(this.mainLayout.getLayout());

        setContentView(this.mainView);
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
                OrbiterConnect orbConnect = new OrbiterConnect();
                orbConnect.connectToOrbiter();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}