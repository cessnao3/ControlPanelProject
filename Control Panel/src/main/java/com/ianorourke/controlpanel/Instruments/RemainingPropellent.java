package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterData;

import android.content.Context;

public class RemainingPropellent extends RectangleLayout {
    public RemainingPropellent(Context context, int size) {
        super(context, size);

        this.setTextSize(24.0f);

        this.updateRectDisplay();
    }

    @Override
    public void updateRectDisplay() {
        this.setText(OrbiterData.getRemainingPropTime());
    }
}