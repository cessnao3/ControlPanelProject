package com.ianorourke.controlpanel.Instruments;

import android.content.Context;

import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterData;

public class AttitudeMode extends RectangleLayout {
    public AttitudeMode(Context context, int size) {
        super(context, size);

        this.setTextSize(24.0f);

        this.updateRectDisplay();
    }

    @Override
    public void updateRectDisplay() {
        if (OrbiterData.engine.attMode == 1) {
            this.setText("Rotation");
        } else if (OrbiterData.engine.attMode == 2) {
            this.setText("Translation");
        } else {
            this.setText("No Data");
        }

    }

    @Override
    public void onTouch() {
        OrbiterMessages.addMessage("FOCUS:ToggleAttitudeMode");
    }
}