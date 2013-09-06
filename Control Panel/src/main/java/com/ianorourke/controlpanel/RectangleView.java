package com.ianorourke.controlpanel;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.view.*;
import android.util.AttributeSet;

import android.graphics.*;

public class RectangleView extends View {
    public static enum RectColors {RED, BLUE};

    float size = 0;
    RectColors color;

    public RectangleView(Context context, float size, RectColors color) {
        super(context);

        this.color = color;

        this.size = size;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Rect rect = new Rect(0, 0, (int) size, (int) size);

        Paint p = new Paint();

        switch (this.color) {
            case RED:
                p.setColor(Color.RED);
                break;
            case BLUE:
                p.setColor(Color.BLUE);
                break;
            default:
                p.setColor(Color.RED);
                break;
        }
        //p.setColor(Color.RED);

        canvas.drawRect(rect, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.setX(event.getRawX() - this.getMeasuredWidth() / 2);
        this.setY(event.getRawY() - this.getMeasuredHeight());

        return true;
    }
}
