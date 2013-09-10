package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.view.*;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class RectangleView extends View {
    private int size;

    public int color;

    public RectangleView(Context context, int size) {
        super(context);
        this.size = size;

        this.setPadding(0, 0, 0, 0);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint p = new Paint();

        if (color % 3 == 0) {
            p.setColor(Color.RED);
        } else if (color % 2 == 0) {
            p.setColor(Color.GREEN);
        } else {
            p.setColor(Color.BLUE);
        }

        //p.setColor(Color.RED);

        canvas.drawRect(0, 0, size, size, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        int[] screenLocation = new int[2];
        this.getLocationOnScreen(screenLocation);

        float displacementX = event.getRawX() - screenLocation[0];
        float displacementY = event.getRawY() - screenLocation[1];

        this.setX(toCenterPoint(this.getX() + displacementX));
        this.setY(toCenterPoint(this.getY() + displacementY));

        return true;
    }

    public void setCenter(PointF p) {
        this.setX(toCenterPoint(p.x));
        this.setY(toCenterPoint(p.y));
    }

    public PointF getCenter() {
        //return new PointF(this.getX() - this.getWidth() / 2, this.getY() - this.getHeight() / 2);
        return new PointF(toCornerPoint(this.getX()), toCornerPoint(this.getY()));
    }

    public int getSize() {
        return this.size;
    }

    private float toCenterPoint(float coord) {
        return coord - this.size / 2;
    }

    private float toCornerPoint(float coord) {
        return coord - this.size / 2;
    }
}
