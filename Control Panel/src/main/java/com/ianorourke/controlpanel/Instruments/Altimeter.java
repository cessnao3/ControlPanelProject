package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterData;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

public class Altimeter extends RectangleLayout {
    public Altimeter(Context context, int size) {
        super(context, size);

        Paint p = new Paint();
        p.setColor(Color.RED);

        this.setColor(p);
        this.message = OrbiterMessages.getAltitudeHandle();

        this.setTextSize(24.0f);

        this.updateRectDisplay();
    }

    @Override
    public void updateRectDisplay() {
        this.setText(OrbiterData.getAltitudeString());
    }
}
