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

    public RectangleLayout(Context context, int size) {
        layout = new RelativeLayout(context);

        this.size = size;

        rectView = new RectangleView(context);

        textView = new TextView(context);
        textView.setX(0.0f);
        textView.setY(0.0f);

        textView.setWidth(size);
        textView.setHeight(size);

        textView.setText("Hello, World!");
        textView.setTextSize(30.0f);

        layout.addView(rectView);
        layout.addView(textView);
    }

    public String getText() {
        return textView.getText().toString();
    }

    public void setText(String newText) {
        textView.setText(newText);
    }

    public int getSize() {
        return this.size;
    }

    public RectangleView getRectangleView() {
        return this.rectView;
    }

    private void alignSelf() {
        GridController.alignObject(this);
    }

    public PointF getCenter() {
        return new PointF(toCenterPoint(this.layout.getX()), toCenterPoint(this.layout.getY()));
    }

    public void setCenter(PointF p) {
        this.layout.setX(toCornerPoint(p.x));
        this.layout.setY(toCornerPoint(p.y));
    }

    public void setCenter(float x, float y) {
        this.layout.setX(toCornerPoint(x));
        this.layout.setY(toCornerPoint(y));
    }

    private float toCenterPoint(float coord) {
        return coord + this.size / 2;
    }

    private float toCornerPoint(float coord) {
        return coord - this.size / 2;
    }

    public void removeSelf() {
        GridController.deleteRect(this);

        ViewGroup viewParent = (ViewGroup) this.layout.getParent();
        if (viewParent != null) viewParent.removeView(layout);
    }

    //RectangleView Class

    public class RectangleView extends View {
        private final Rect rect;
        private boolean isRectEnabled = true;

        public RectangleView(Context context) {
            super(context);

            this.setPadding(0, 0, 0, 0);

            rect = new Rect(0, 0, size, size);
        }

        @Override
        public void onDraw(Canvas canvas) {
            int color = new Integer((int) (Math.random() * 9.0)).intValue();

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
                int[] screenLocation = new int[2];
                this.getLocationOnScreen(screenLocation);

                //TODO: Bring Rect to Front
                layout.getParent().bringChildToFront(layout);

                float displacementX = event.getRawX() - screenLocation[0];
                float displacementY = event.getRawY() - screenLocation[1];

                layout.setX(toCornerPoint(layout.getX() + displacementX));
                layout.setY(toCornerPoint(layout.getY() + displacementY));

                //TODO: Clean Deletion Code
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (layout.getX() < 0 && layout.getY() < 0) {
                        if (isRectEnabled && event.getAction() == MotionEvent.ACTION_UP) {
                            removeSelf();

                            isRectEnabled = false;
                        }
                    } else {
                        alignSelf();
                    }
                }

                return true;
            } else return false;
        }
    }
}
