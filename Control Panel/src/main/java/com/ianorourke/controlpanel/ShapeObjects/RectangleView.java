package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

public class RectangleView extends View {

    private final Rect rect;
    private final int size;

    public int color;

    private boolean isRectEnabled = true;

    public RectangleView(Context context, int size) {
        super(context);
        this.size = size;

        this.setPadding(0, 0, 0, 0);

        rect = new Rect(0, 0, size, size);
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

        canvas.drawRect(this.rect, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (rect.contains((int)event.getX(), (int)event.getY())) {
            //super.onTouchEvent(event);

            int[] screenLocation = new int[2];
            this.getLocationOnScreen(screenLocation);

            float displacementX = event.getRawX() - screenLocation[0];
            float displacementY = event.getRawY() - screenLocation[1];

            this.setX(toCornerPoint(this.getX() + displacementX));
            this.setY(toCornerPoint(this.getY() + displacementY));

            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                if (this.getX() < 0 && this.getY() < 0) {
                    if (isRectEnabled && event.getAction() == MotionEvent.ACTION_UP) {
                        this.removeSelf();

                        isRectEnabled = false;
                    }
                } else {
                    GridView.Grid.alignObject(this);
                }
            }

            return true;
        } else return false;
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

    private void removeSelf() {
        GridView.Grid.deleteRect(this);
        GridView.Grid.currentRect--;

        ViewGroup viewParent = (ViewGroup) this.getParent();

        viewParent.removeView(this);
    }
}
