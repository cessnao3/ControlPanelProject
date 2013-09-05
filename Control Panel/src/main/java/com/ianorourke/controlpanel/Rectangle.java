package com.ianorourke.controlpanel;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.view.*;
import android.util.AttributeSet;

import android.graphics.*;

/**
 * Created by ian on 9/5/13.
 */
public class Rectangle extends View {
    public Rectangle(Context context) {
        super(context);

        Rect rect = new Rect(0, 0, 50, 50);
        ShapeDrawable drawable = new ShapeDrawable();
    }

    @Override
    public void onDraw(Canvas canvas) {

    }
}
