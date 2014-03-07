package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterStatus;

import android.content.Context;

public class NameDisplay extends RectangleLayout {
    public NameDisplay(Context context, int size) {
        super(context, size);
        setName("Name Display");

        this.createTextView();
        this.updateRectDisplay();
    }

    @Override
    public void updateRectDisplay() {
        this.setText(OrbiterStatus.name);
    }
}