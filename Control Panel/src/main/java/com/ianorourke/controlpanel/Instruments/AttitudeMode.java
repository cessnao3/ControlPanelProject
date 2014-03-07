package com.ianorourke.controlpanel.Instruments;

import android.content.Context;

import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterStatus;

public class AttitudeMode extends RectangleLayout {
    public AttitudeMode(Context context, int size) {
        super(context, size);
        setName("Attitude Mode");

        this.createTextView();
        this.setTextSize(24.0f);

        this.updateRectDisplay();
    }

    @Override
    public void updateRectDisplay() {
        if (OrbiterStatus.attMode == 1) {
            this.setText("Rotation");
        } else if (OrbiterStatus.attMode == 2) {
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