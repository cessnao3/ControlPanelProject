package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;
import com.ianorourke.controlpanel.ShapeObjects.*;
import com.ianorourke.controlpanel.Orbiter.OrbiterData;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

public class ToggleHud extends RectangleLayout {
    public ToggleHud(Context context, int size) {
        super(context, size);

        Paint p = new Paint();
        p.setColor(Color.LTGRAY);

        this.setColor(p);
        this.message = null;

        this.textView.setTextSize(24.0f);

        this.setText("Toggle HUD Color");

        this.updateRectDisplay();
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void onTouch() {
        OrbiterMessages.addMessage("ORB:ToggleHUDColor");
    }
}