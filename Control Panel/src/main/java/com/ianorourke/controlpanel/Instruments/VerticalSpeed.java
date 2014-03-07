package com.ianorourke.controlpanel.Instruments;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ianorourke.controlpanel.Orbiter.OrbiterStatus;
import com.ianorourke.controlpanel.R;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout;

public class VerticalSpeed extends RectangleLayout {

    private final double MAX_SPEED = 50.0;

    private ImageView backgroundImage;
    private ImageView vsIndicatorHand;

    public VerticalSpeed(Context context, int size) {
        super(context, size);
        setName("Vertical Speed");

        //TODO: New Graphics

        //Background
        backgroundImage = new ImageView(this.getContext());
        backgroundImage.setImageResource(R.drawable.alt_background);
        layout.addView(backgroundImage, new RelativeLayout.LayoutParams(this.getSize(), this.getSize()));

        //VS Hand
        vsIndicatorHand = new ImageView(this.getContext());
        vsIndicatorHand.setImageResource(R.drawable.long_hand);

        int lWidth = (int) (0.95 * this.getSize() / 2.0);
        int lHeight = 0;

        try {
            lHeight = (vsIndicatorHand.getDrawable().getIntrinsicHeight() * lWidth / vsIndicatorHand.getDrawable().getIntrinsicWidth());
        } catch (NullPointerException e) {e.printStackTrace();}

        layout.addView(vsIndicatorHand, new RelativeLayout.LayoutParams(lWidth, lHeight));

        float lX = 0.05f * this.getSize() / 2.0f;
        float lY = vsIndicatorHand.getHeight() / 2.0f;

        vsIndicatorHand.setPivotX(lX);
        vsIndicatorHand.setPivotY(lY);
        vsIndicatorHand.setX((float) (this.getSize() / 2.0 - lX));
        vsIndicatorHand.setY((float) (this.getSize() / 2.0 - lY));

        //Update
        this.updateRectDisplay();
    }

    @Override
    public void updateRectDisplay() {
        double vs = OrbiterStatus.airspeedVector[2];

        double angle = 0.0;

        if (Math.abs(vs) < MAX_SPEED) {
            angle = 180.0 + 180.0 * vs / MAX_SPEED;
        }

        if (vsIndicatorHand != null && vsIndicatorHand.getParent() != null) vsIndicatorHand.setRotation((float) angle);
    }
}
