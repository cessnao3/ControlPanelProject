package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterData;
import com.ianorourke.controlpanel.R;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Altimeter extends RectangleLayout {
    ImageView backgroundImage;
    ImageView longHandImage;
    ImageView shortHandImage;

    int timerSteps = 0;

    public Altimeter(Context context, int size) {
        super(context, size);

        this.setTextSize(24.0f);

        this.updateRectDisplay();

        //Background

        backgroundImage = new ImageView(this.getContext());
        backgroundImage.setImageResource(R.drawable.alt_background);
        //layout.addView(backgroundImage, new RelativeLayout.LayoutParams(this.getSize(), this.getSize()));

        //Long Hand

        longHandImage = new ImageView(this.getContext());
        longHandImage.setImageResource(R.drawable.long_hand);

        int lWidth = (int) (0.95 * this.getSize() / 2.0);
        int lHeight = (longHandImage.getDrawable().getIntrinsicHeight() * lWidth / longHandImage.getDrawable().getIntrinsicWidth());
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
        int height = (shortHandImage.getDrawable().getIntrinsicHeight() * width / shortHandImage.getDrawable().getIntrinsicWidth());
        layout.addView(shortHandImage, new RelativeLayout.LayoutParams(width, height));

        float x = 0.05f * this.getSize() / 2.0f;
        float y = shortHandImage.getHeight() / 2.0f;

        shortHandImage.setPivotX(x);
        shortHandImage.setPivotY(y);
        shortHandImage.setX((float) (this.getSize() / 2.0 - x));
        shortHandImage.setY((float) (this.getSize() / 2.0 - y));

        //Text View Stuffs

        textView.bringToFront();
        textView.requestFocus();

        //TODO: Flip Long/Short Hand
    }

    @Override
    public void updateRectDisplay() {
        String altitude = String.format("%6.3e", OrbiterData.vessel.altitude);
        altitude = altitude.replace(altitude.substring(altitude.indexOf("e")), "");

        //TODO: Set to Kilometer x10^x Intervals

        float rotation = Float.valueOf(altitude).floatValue() * 36.0f - 90.0f;
        if (longHandImage != null && longHandImage.getParent() != null) longHandImage.setRotation(rotation);

        rotation = (Float.valueOf(altitude).floatValue() - Integer.valueOf(altitude.substring(0, 1)).intValue()) * 360.0f - 90.0f;
        if (shortHandImage != null && shortHandImage.getParent() != null) shortHandImage.setRotation(rotation);

        this.setText(OrbiterData.vessel.altitude);
    }
}