package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;
import com.ianorourke.controlpanel.ShapeObjects.*;

import android.content.Context;

public class ToggleHud extends RectangleLayout {
    public ToggleHud(Context context, int size) {
        super(context, size);
        setName("Toggle HUD Color");

        this.createTextView();
        this.setTextSize(24.0f);
        this.setText("Toggle HUD Color");

        this.updateRectDisplay();
    }

    @Override
    public void onTouch() {
        OrbiterMessages.addMessage("ORB:ToggleHUDColor");
    }
}