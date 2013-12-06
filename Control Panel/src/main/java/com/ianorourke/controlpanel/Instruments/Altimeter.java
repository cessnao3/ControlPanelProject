package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterData;
import com.ianorourke.controlpanel.R;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout;

import android.content.Context;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import java.lang.Runnable;

public class Altimeter extends RectangleLayout {
    ImageView backgroundImage;
    ImageView longHandImage;

    public Altimeter(Context context, int size) {
        super(context, size);

        this.setTextSize(24.0f);

        this.updateRectDisplay();

        backgroundImage = new ImageView(this.getContext());
        backgroundImage.setImageResource(R.drawable.alt_background);
        layout.addView(backgroundImage);

        longHandImage = new ImageView(this.getContext());
        longHandImage.setImageResource(R.drawable.long_hand);
        layout.addView(longHandImage);

        //TODO: Flip Long/Short Hand
    }

    @Override
    public void onTouch() {
        final Handler timerHandler = new Handler();
        final Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                float rotation = longHandImage.getRotation();

                rotation += 5;

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
        timer.schedule(task, 0, 100);
    }

    @Override
    public void updateRectDisplay() {
        String altitude = String.format("%6.3e", OrbiterData.vessel.altitude);
        altitude = altitude.replace(altitude.substring(altitude.indexOf("e")), "");

        this.setText(altitude);
    }
}