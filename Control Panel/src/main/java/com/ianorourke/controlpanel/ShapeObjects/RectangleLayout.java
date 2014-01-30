package com.ianorourke.controlpanel.ShapeObjects;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ianorourke.controlpanel.R;

import java.util.Calendar;

public class RectangleLayout {
    public RectangleView rectView;

    public RelativeLayout layout;
    public Paint paintColor = new Paint();

    private final int size;

    public RectangleLayout(Context context, int size) {
        layout = new RelativeLayout(context);
        this.size = size;

        rectView = new RectangleView(context);
        layout.addView(rectView, new RelativeLayout.LayoutParams(size, size));
    }

    //TextView Setting

    public void createTextView() {
        if (layout.findViewById(R.id.rect_text_view) != null || layout.getContext() == null) return;

        TextView textView = new TextView(layout.getContext());

        textView.setId(R.id.rect_text_view);

        textView.setX(0.0f);
        textView.setY(0.0f);

        textView.setWidth(size);
        textView.setHeight(size);

        textView.setTextSize(30.0f);

        layout.addView(textView);
    }

    public TextView getTextView() {
        return (TextView) layout.findViewById(R.id.rect_text_view);
    }

    public void setText(String newText) {
        if (getTextView() != null) getTextView().setText((newText != null) ? newText : "");
    }

    public void setText(double d) {
        this.setText(Double.valueOf(d).toString());
    }

    public void setTextSize(float f) {
        if (getTextView() != null) getTextView().setTextSize(f);
    }

    public int getSize() {
        return this.size;
    }

    public Context getContext() {
        return this.rectView.getContext();
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

    //RectangleView Class

    public class RectangleView extends View {
        private final Rect rect;

        public RectangleView(Context context) {
            super(context);

            rect = new Rect(0, 0, size, size);
        }

        @Override
        public void onDraw(Canvas canvas) {
            paintColor.setColor(Color.LTGRAY);
            canvas.drawRect(this.rect, paintColor);
        }

        private final int MAX_DP = 12;

        private float startX, startY;
        boolean shouldClick = true;

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                startX = event.getRawX();
                startY = event.getRawY();

                shouldClick = true;
            }

            if (GridController.isEditing) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (distancePxToDp(startX, event.getRawX(), startY, event.getRawY()) > MAX_DP) shouldClick = false;

                    if (!shouldClick) {
                        int[] screenLocation = new int[2];
                        this.getLocationOnScreen(screenLocation);

                        float displacementX = event.getRawX() - screenLocation[0];
                        float displacementY = event.getRawY() - screenLocation[1];

                        Log.v("cp", Float.valueOf(event.getRawY()).toString());

                        setCenter(new PointF(layout.getX() + displacementX, layout.getY() + displacementY));
                    }
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) alignSelf();

                //Click Event
                if (event.getAction() == MotionEvent.ACTION_UP && shouldClick) {
                    if (!GridController.isEditing) onTouch();
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder
                                .setMessage("Delete Rect?")
                                .setTitle("Confirm")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        removeSelf();
                                    }
                                })
                                .setNegativeButton("No", null);
                        builder.create().show();
                    }
                }

                return true;
            } else return false;
        }

        private float distancePxToDp(float x1, float x2, float y1, float y2) {
            float distance = (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
            return distance / getResources().getDisplayMetrics().density;
        }
    }
}
