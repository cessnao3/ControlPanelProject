package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterStatus;

import android.content.Context;

public class Velocity extends RectangleLayout {
    //public enum velocityModes {TAS, IAS, OS, GS};

    //0: Airspeed
    //1: Indicated Airspeed
    //2: Orbit Speed
    //3: Ground Speed

    String[] velModes = {"TAS", "IAS", "OS", "GS"};
    int currentMode = 0;

    public Velocity(Context context, int size) {
        super(context, size);

        this.createTextView();
        this.setTextSize(24.0f);

        this.updateRectDisplay();
    }

    @Override
    public void updateRectDisplay() {
        this.setText(OrbiterStatus.airspeed);

        double vel;

        switch (currentMode) {
            case 0:
                vel = OrbiterStatus.airspeed;
                break;
            case 1:
                vel = OrbiterStatus.indicatedAirspeed;
                break;
            case 2:
                vel = OrbiterStatus.orbitSpeed;
                break;
            case 3:
                vel = OrbiterStatus.groundSpeed;
                break;
            default:
                currentMode = 0;
                vel = OrbiterStatus.airspeed;
        }

        this.setText(velModes[currentMode] + "\n" + Double.valueOf(vel).toString());
    }

    @Override
    public void onTouch() {
        currentMode++;
        if (currentMode > 3) currentMode = 0;

        updateRectDisplay();
    }
}
