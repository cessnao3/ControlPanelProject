package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RectangleLayout {
    public RectangleView rectView;
    private TextView textView;

    public RelativeLayout layout;

    private final int size;

    protected String message = null;

    public RectangleLayout(Context context, int size) {
        layout = new RelativeLayout(context);

        this.size = size;

        rectView = new RectangleView(context);

        textView = new TextView(context);
        textView.setX(0.0f);
        textView.setY(0.0f);

        textView.setWidth(size);
        textView.setHeight(size);

        textView.setTextSize(30.0f);

        layout.addView(rectView);
        layout.addView(textView);
    }

    //TextView Setting

    public void setText(String newText) {
        textView.setText(newText);
    }

    public void setTextSize(float f) {
        textView.setTextSize(f);
    }

    public int getSize() {
        return this.size;
    }

    public String getMessage() {
        return this.message;
    }

    public void onTouch() {
        //Code for Subclasses
    }

    public void updateRectDisplay() {
        //Code for Subclasses
    }

    private void alignSelf() {
        GridController.alignObject(this);
    }

    //Points

    public PointF getCenter() {
        return new PointF(toCenterPoint(this.layout.getX()), toCenterPoint(this.layout.getY()));
    }

    public void setCenter(PointF p) {
        this.layout.setX(toCornerPoint(p.x));
        this.layout.setY(toCornerPoint(p.y));
    }

    private float toCenterPoint(float coord) {
        return coord + this.size / 2;
    }

    private float toCornerPoint(float coord) {
        return coord - this.size / 2;
    }

    //Removal
    public void removeSelf() {
        GridController.deleteRect(this);

        ViewGroup viewParent = (ViewGroup) this.layout.getParent();
        if (viewParent != null) viewParent.removeView(layout);
    }

    //Colors
    private Paint paintColor = null;

    public void setColor(Paint p) {
        if (p != null) paintColor = p;
    }

    //RectangleView Class

    public class RectangleView extends View {
        private final Rect rect;

        public RectangleView(Context context) {
            super(context);

            rect = new Rect(0, 0, size, size);
        }

        @Override
        public void onDraw(Canvas canvas) {
            if (paintColor == null) {
                paintColor = new Paint();
                paintColor.setColor(Color.WHITE);
            }

            canvas.drawRect(this.rect, paintColor);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (GridController.isEditing) {
                int[] screenLocation = new int[2];
                this.getLocationOnScreen(screenLocation);

                //TODO: Bring Rect to Front

                float displacementX = event.getRawX() - screenLocation[0];
                float displacementY = event.getRawY() - screenLocation[1];

                layout.setX(toCornerPoint(layout.getX() + displacementX));
                layout.setY(toCornerPoint(layout.getY() + displacementY));

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (layout.getX() < 0 && layout.getY() < 0) {
                        if (event.getAction() == MotionEvent.ACTION_UP) removeSelf();
                    } else alignSelf();
                }
            }

            if (event.getAction() == MotionEvent.ACTION_UP) onTouch();

            return true;
        }
    }
}
