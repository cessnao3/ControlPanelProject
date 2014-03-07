package com.ianorourke.controlpanel.Instruments;

import android.content.Context;

import com.ianorourke.controlpanel.Orbiter.OrbiterStatus;
import com.ianorourke.controlpanel.ShapeObjects.RectangleLayout;

public class Machometer extends RectangleLayout {
    public Machometer(Context context, int size) {
        super(context, size);
        setName("Machometer");

        this.createTextView();
        this.updateRectDisplay();
    }

    @Override
    public void updateRectDisplay() {
        this.setText(OrbiterStatus.mach);
    }
}
