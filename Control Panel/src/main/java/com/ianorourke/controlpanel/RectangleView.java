package com.ianorourke.controlpanel;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.view.*;
import android.util.AttributeSet;

import android.graphics.*;

public class RectangleView extends View {
    public RectangleView(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Rect rect = new Rect(0, 0, 50, 50);

        Paint p = new Paint();
        p.setColor(Color.RED);

        canvas.drawRect(rect, p);
    }
}
