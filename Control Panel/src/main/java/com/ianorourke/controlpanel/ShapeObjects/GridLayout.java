package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ianorourke.controlpanel.Instruments.*;

public class GridLayout implements GridView.IGridVew, GridController.IGridController {
    private RelativeLayout layout;
    private GridView mainView;

    public void testInterface() {

    }

    public GridLayout(Context context) {
        layout = new RelativeLayout(context);
        mainView = new GridView(context, this);

        layout.addView(mainView);
    }

    public void finishedMeasuring() {
        Log.v("cp", "Finished Measuring");

        //TODO: Re-Add Objects when Orientation Changed

        /*
        if (GridController.gridPointList.size() == 0) return;

        for (int i = 0; i < GridController.gridPointList.size(); i++) {
            GridObject point = GridController.gridPointList.get(i);

            if (point.hasObject()) addLayout(point.getObject());
        }
        */
    }

    public RelativeLayout getLayout() {
        return layout;
    }

    //Rect Types
    public void createAltimeter() {
        Altimeter alt = new Altimeter(layout.getContext(), GridController.rectSize);
        addLayout(alt);
    }

    public void createAirspeed() {
        Airspeed aSpd = new Airspeed(layout.getContext(), GridController.rectSize);
        addLayout(aSpd);
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
