package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RectangleLayout {
    public RectangleView rectView;
    public TextView textView;
    public RelativeLayout layout;

    public RectangleLayout(Context context, int size) {
        layout = new RelativeLayout(context);

        rectView = new RectangleView(context, size);

        textView = new TextView(context);
        textView.setX(0.0f);
        textView.setY(0.0f);

        textView.setText("Hello, World!");
        textView.setTextSize(100.0f);

        layout.addView(rectView);
        layout.addView(textView);

        textView.bringToFront();
    }

}
