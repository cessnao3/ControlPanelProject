package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.Orbiter.OrbiterConnect;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;

import android.util.Log;

public class MainActivity extends Activity {
    private String labelText = "Hello, World!";

    private GridLayout mainLayout;

    private OrbiterConnect orbiterConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.mainLayout == null || this.mainLayout.getLayout() == null) {
            this.mainLayout = new GridLayout(this);

            setContentView(this.mainLayout.getLayout());
        }

        if (orbiterConnect == null) orbiterConnect = new OrbiterConnect();

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
                orbiterConnect.connectToOrbiter();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}