package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterData;

import android.content.Context;

public class Altimeter extends RectangleLayout {
    public final String message = "FOCUS:Alt";

    public Altimeter(Context context, int size) {
        super(context, size);

        this.setColor(3);
    }

    public void updateDisplay() {
        //this.setText(new Double(OrbiterData.altitude).toString());
    }
}
