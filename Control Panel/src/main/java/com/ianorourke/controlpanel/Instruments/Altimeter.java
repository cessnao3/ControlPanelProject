package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterData;
import com.ianorourke.controlpanel.R;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout;

import java.lang.Runnable;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Altimeter extends RectangleLayout {
    ImageView backgroundImage;
    ImageView longHandImage;

    int timerSteps = 0;

    public Altimeter(Context context, int size) {
        super(context, size);

        this.setTextSize(24.0f);

        this.updateRectDisplay();

        backgroundImage = new ImageView(this.getContext());
        backgroundImage.setImageResource(R.drawable.alt_background);
        layout.addView(backgroundImage, new RelativeLayout.LayoutParams(this.getSize(), this.getSize()));

        longHandImage = new ImageView(this.getContext());
        longHandImage.setImageResource(R.drawable.long_hand);

        int width = (int) (0.95 * this.getSize() / 2.0);
        int height = (longHandImage.getDrawable().getIntrinsicHeight() * width / longHandImage.getDrawable().getIntrinsicWidth());
        layout.addView(longHandImage, new RelativeLayout.LayoutParams(width, height));

        float x = 0.05f * this.getSize() / 2.0f;
        float y = longHandImage.getHeight() / 2.0f;

        longHandImage.setPivotX(x);
        longHandImage.setPivotY(y);
        longHandImage.setX((float) (this.getSize() / 2.0 - x));
        longHandImage.setY((float) (this.getSize() / 2.0 - y));

        //TODO: Flip Long/Short Hand
    }

    @Override
    public void onTouch() {
        if (timerSteps > 2) return;

        final Handler timerHandler = new Handler();
        final Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                float rotation = longHandImage.getRotation();

                rotation += 1;

                if (rotation > 360) rotation -= 360;

                longHandImage.setRotation(rotation);
            }
        };

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timerHandler.post(timerRunnable);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 10);

        timerSteps++;
    }

    @Override
    public void updateRectDisplay() {
        String altitude = String.format("%6.3e", OrbiterData.vessel.altitude);
        altitude = altitude.replace(altitude.substring(altitude.indexOf("e")), "");

        this.setText(altitude);
    }
}