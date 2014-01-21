package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ianorourke.controlpanel.Instruments.*;

public class GridLayout {
    private RelativeLayout layout;
    private GridView mainView;

    public GridLayout(Context context) {
        layout = new RelativeLayout(context);
        mainView = new GridView(context);

        layout.addView(mainView);
    }

    public RelativeLayout getLayout() {
        return layout;
    }

    //Rect Types
    public void createAltimeter() {
        Altimeter alt = new Altimeter(layout.getContext(), GridController.rectSize);
        addLayout(alt);
    }

    public void createVelocity() {
        Velocity vel = new Velocity(layout.getContext(), GridController.rectSize);
        addLayout(vel);
    }

    public void createNameDisplay() {
        NameDisplay name = new NameDisplay(layout.getContext(), GridController.rectSize);
        addLayout(name);
    }

    public void createToggleHud() {
        ToggleHud hud = new ToggleHud(layout.getContext(), GridController.rectSize);
        addLayout(hud);
    }

    public void createPropFlow() {
        RemainingPropellent prop = new RemainingPropellent(layout.getContext(), GridController.rectSize);
        addLayout(prop);
    }

    public void createAttitudeMode() {
        AttitudeMode att = new AttitudeMode(layout.getContext(), GridController.rectSize);
        addLayout(att);
    }

    public void createMachometer() {
        Machometer mach = new Machometer(layout.getContext(), GridController.rectSize);
        addLayout(mach);
    }

    public void createVerticalSpeed() {
        VerticalSpeed vs = new VerticalSpeed(layout.getContext(), GridController.rectSize);
        addLayout(vs);
    }

    //Layout Params
    private void addLayout(RectangleLayout rect) {
        if (rect == null) return;
        if (GridController.getNumRects() >= GridController.gridPointList.size()) return;

        GridController.addRectangle(rect);

        ViewGroup viewParent = (ViewGroup) rect.layout.getParent();
        if (viewParent != null) viewParent.removeView(rect.layout);

        layout.addView(rect.layout, new ViewGroup.LayoutParams(rect.getSize(), rect.getSize()));

        Log.v("cp", Integer.valueOf(GridController.getNumRects()).toString());
    }
}
