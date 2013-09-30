package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout.*;

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
    protected void onStart() {
        super.onStart();

        //TODO: Clean Code
        //TODO: Separate Grid from GridView

        this.mainView = new GridView(this);
        GridView.Grid.clearGridPoints();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}