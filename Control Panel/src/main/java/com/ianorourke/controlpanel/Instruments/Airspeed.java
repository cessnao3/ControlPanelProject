package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterStatus;

import android.content.Context;

public class Airspeed extends RectangleLayout {
    public Airspeed(Context context, int size) {
        super(context, size);

        this.createTextView();
        this.setTextSize(24.0f);

        this.updateRectDisplay();
    }

    @Override
    public void updateRectDisplay() {
        this.setText(OrbiterStatus.airspeed);
    }
}
