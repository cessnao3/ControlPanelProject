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

        RectangleView rect = new RectangleView(this);
        rect.setX(50);
        rect.setY(50);

        ViewGroup.LayoutParams rectangleParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        addContentView(rect, rectangleParams);
    }
    
}
