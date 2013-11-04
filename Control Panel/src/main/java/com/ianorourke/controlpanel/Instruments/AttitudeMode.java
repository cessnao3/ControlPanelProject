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
        this.setText(OrbiterData.engine.attMode);
    }

    @Override
    public void onTouch() {
        OrbiterMessages.addMessage("FOCUS:ToggleAttitudeMode");
    }
}