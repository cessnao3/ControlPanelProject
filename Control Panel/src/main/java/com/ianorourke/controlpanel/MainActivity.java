package com.ianorourke.controlpanel;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.view.ViewGroup.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        float defaultSize = 300;

        RectangleView rect = new RectangleView(this, defaultSize, RectangleView.RectColors.RED);
        RectangleView rect2 = new RectangleView(this, defaultSize, RectangleView.RectColors.BLUE);

        rect2.setX((int) defaultSize);

        ViewGroup.LayoutParams rectangleParams = new ViewGroup.LayoutParams((int) defaultSize, (int) defaultSize);

        addContentView(rect, rectangleParams);
        addContentView(rect2, rectangleParams);
    }
    
}
