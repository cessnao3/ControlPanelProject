package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterData;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

public class NameDisplay extends RectangleLayout {
    public NameDisplay(Context context, int size) {
        super(context, size);

        Paint p = new Paint();
        p.setColor(Color.BLUE);

        this.setColor(p);
        this.message = OrbiterMessages.getNameHandle();

        this.updateRectDisplay();
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void updateRectDisplay() {
        this.setText(OrbiterData.getNameString());
    }
}
