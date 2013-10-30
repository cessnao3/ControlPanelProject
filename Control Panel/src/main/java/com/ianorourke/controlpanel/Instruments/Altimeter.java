package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterData;

import android.content.Context;

public class Altimeter extends RectangleLayout {
    public Altimeter(Context context, int size) {
        super(context, size);

        this.setTextSize(24.0f);

        this.updateRectDisplay();
    }

    @Override
    public void updateRectDisplay() {
        this.setText(OrbiterData.vessel.altitude);
    }
}
