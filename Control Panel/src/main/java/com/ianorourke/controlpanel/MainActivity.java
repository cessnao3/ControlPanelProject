package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;

public class MainActivity extends Activity {

    private GridView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainView = new GridView(this);
        GridController.clearGridPoints();

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
                RectangleLayout rectLayout = this.mainView.createRect();

                if (rectLayout != null) {
                    RectangleView rect = rectLayout.getRectangleView();

                    addContentView(rectLayout.layout, new ViewGroup.LayoutParams(rectLayout.getSize(), rectLayout.getSize()));
                }
                return true;
            case R.id.menu_changetext:

                if (Orbiter.text.equals("HI!")) {
                    Orbiter.changeText("Hello, World!");
                } else {
                    Orbiter.changeText("HI!");
                }

                GridController.updateRects(Orbiter.text);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}