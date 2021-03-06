package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterStatus;
import com.ianorourke.controlpanel.R;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Altimeter extends RectangleLayout {
    ImageView backgroundImage;
    ImageView longHandImage;
    ImageView shortHandImage;

    public Altimeter(Context context, int size) {
        super(context, size);

        this.updateRectDisplay();

        //Background

        backgroundImage = new ImageView(this.getContext());
        backgroundImage.setImageResource(R.drawable.alt_background);
        layout.addView(backgroundImage, new RelativeLayout.LayoutParams(this.getSize(), this.getSize()));

        //Long Hand

        longHandImage = new ImageView(this.getContext());
        longHandImage.setImageResource(R.drawable.long_hand);

        int lWidth = (int) (0.95 * this.getSize() / 2.0);
        int lHeight = 0;

        try {
            lHeight = (longHandImage.getDrawable().getIntrinsicHeight() * lWidth / longHandImage.getDrawable().getIntrinsicWidth());
        } catch (NullPointerException e) {e.printStackTrace();}

        layout.addView(longHandImage, new RelativeLayout.LayoutParams(lWidth, lHeight));

        float lX = 0.05f * this.getSize() / 2.0f;
        float lY = longHandImage.getHeight() / 2.0f;

        longHandImage.setPivotX(lX);
        longHandImage.setPivotY(lY);
        longHandImage.setX((float) (this.getSize() / 2.0 - lX));
        longHandImage.setY((float) (this.getSize() / 2.0 - lY));

        //Short Hand

        shortHandImage = new ImageView(this.getContext());
        shortHandImage.setImageResource(R.drawable.long_hand);

        int width = (int) (0.6 * this.getSize() / 2.0);
        int height = 0;

        try {
            height = (shortHandImage.getDrawable().getIntrinsicHeight() * width / shortHandImage.getDrawable().getIntrinsicWidth());
        } catch (NullPointerException e) {e.printStackTrace();}

        layout.addView(shortHandImage, new RelativeLayout.LayoutParams(width, height));

        float x = 0.05f * this.getSize() / 2.0f;
        float y = shortHandImage.getHeight() / 2.0f;

        shortHandImage.setPivotX(x);
        shortHandImage.setPivotY(y);
        shortHandImage.setX((float) (this.getSize() / 2.0 - x));
        shortHandImage.setY((float) (this.getSize() / 2.0 - y));
    }

    @Override
    public void updateRectDisplay() {
        double altitude = InstrumentActions.getInitialNumber(OrbiterStatus.altitude);

        //TODO: Set to Kilometer x10^x Intervals
        //TODO: Check Long/Short Image

        float rotation = (float) altitude * 36.0f - 90.0f;
        if (shortHandImage != null && shortHandImage.getParent() != null) shortHandImage.setRotation(rotation);

        rotation = (float) (altitude - (int) altitude) * 360.0f - 90.0f;
        if (longHandImage != null && longHandImage.getParent() != null) longHandImage.setRotation(rotation);

        //this.setText(OrbiterData.vessel.altitude);
    }
}