package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.view.*;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RectangleView extends View {
    int size;

    //TODO: Fix Layout Orientation

    public RectangleView(Context context, int size) {
        super(context);
        this.size = size;

        this.setPadding(0, 0, 0, 0);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //Rect rect = new Rect( 0,  0, size, size);

        Paint p = new Paint();
        p.setColor(Color.RED);

        canvas.drawRect(0, 0, size, size, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //this.setX(event.getRawX() - this.getWidth() / 2);
        //this.setY(event.getRawY() - this.getHeight() / 2);

        this.setX(event.getRawX());
        this.setY(event.getRawY());

        return true;
    }

    public void setCenter(PointD p) {
        /*
        float x = (float) p.x - (float) this.getWidth() / 2;
        float y = (float) p.y - (float) this.getHeight() / 2;

        this.setX(x);
        this.setY(y);
        */

        this.setX((float) p.x);
        this.setY((float) p.y);
    }

    public PointD getCenter() {
        //return new PointD(this.getX() - this.getWidth() / 2, this.getY() - this.getHeight() / 2);
        return new PointD(this.getX(), this.getY());
    }

    public int getSize() {
        return this.size;
    }
}
