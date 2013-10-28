package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterData;

import android.content.Context;

public class PropFlowRate extends RectangleLayout {
    public PropFlowRate(Context context, int size) {
        super(context, size);

        this.message = OrbiterMessages.getPropFlowHandle();

        this.setTextSize(24.0f);

        this.updateRectDisplay();
    }

    @Override
    public void updateRectDisplay() {
        this.setText(OrbiterData.getPropFlowString());
    }
}