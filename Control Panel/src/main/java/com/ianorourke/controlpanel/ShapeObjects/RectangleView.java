package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.view.*;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import android.view.ViewGroup;

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

        canvas.drawRect(0, 0, size, size, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        int[] screenLocation = new int[2];
        this.getLocationOnScreen(screenLocation);

        float displacementX = event.getRawX() - screenLocation[0];
        float displacementY = event.getRawY() - screenLocation[1];

        this.setX(toCornerPoint(this.getX() + displacementX));
        this.setY(toCornerPoint(this.getY() + displacementY));

        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            Grid.alignObject(this);
        }

        return true;
    }

    public void setCenter(PointF p) {
        this.setX(toCornerPoint(p.x));
        this.setY(toCornerPoint(p.y));
    }

    public PointF getCenter() {
        return new PointF(toCenterPoint(this.getX()), toCenterPoint(this.getY()));
    }

    public int getSize() {
        return this.size;
    }

    private float toCenterPoint(float coord) {
        return coord + this.size / 2;
    }

    private float toCornerPoint(float coord) {
        return coord - this.size / 2;
    }

    public void removeView() {
        ViewGroup viewParent = (ViewGroup) this.getParent();
        viewParent.removeView(this);
    }
}
