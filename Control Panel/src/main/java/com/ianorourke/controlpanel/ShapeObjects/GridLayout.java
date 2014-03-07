package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ianorourke.controlpanel.Instruments.*;
import com.ianorourke.controlpanel.OrbiterSave;

import java.lang.reflect.InvocationTargetException;

public class GridLayout {
    private RelativeLayout layout;
    private GridView mainView;

    final int NO_POS = -1;

    public GridLayout(Context context) {
        layout = new RelativeLayout(context);
        mainView = new GridView(context);

        layout.addView(mainView);
    }

    public RelativeLayout getLayout() {
        return layout;
    }

    public void parseLoadString(String load) {
        if (load == null || load == "") return;

        String[] instruments = load.split("\n");

        for (String i : instruments) {
            String[] values = i.split(OrbiterSave.GRID_SPLIT);
            if (values.length < 2) break;

            RectangleLayout r = null;

            try {
                Class<?> cls = Class.forName(values[0]);
                r = (RectangleLayout) cls.getConstructor(new Class[] {Context.class, int.class}).newInstance(new Object[] {layout.getContext(), GridController.rectSize});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (r != null) addLayout(r, Integer.parseInt(values[1]));
        }
    }

    //Rect Types
    public void createAltimeter() {
        Altimeter alt = new Altimeter(layout.getContext(), GridController.rectSize);
        addLayout(alt, NO_POS);
    }

    public void createVelocity() {
        Velocity vel = new Velocity(layout.getContext(), GridController.rectSize);
        addLayout(vel, NO_POS);
    }

    public void createNameDisplay() {
        NameDisplay name = new NameDisplay(layout.getContext(), GridController.rectSize);
        addLayout(name, NO_POS);
    }

    public void createToggleHud() {
        ToggleHud hud = new ToggleHud(layout.getContext(), GridController.rectSize);
        addLayout(hud, NO_POS);
    }

    public void createPropFlow() {
        RemainingPropellent prop = new RemainingPropellent(layout.getContext(), GridController.rectSize);
        addLayout(prop, NO_POS);
    }

    public void createAttitudeMode() {
        AttitudeMode att = new AttitudeMode(layout.getContext(), GridController.rectSize);
        addLayout(att, NO_POS);
    }

    public void createMachometer() {
        Machometer mach = new Machometer(layout.getContext(), GridController.rectSize);
        addLayout(mach, NO_POS);
    }

    public void createVerticalSpeed() {
        VerticalSpeed vs = new VerticalSpeed(layout.getContext(), GridController.rectSize);
        addLayout(vs, NO_POS);
    }

    //Layout Params
    private void addLayout(RectangleLayout rect, int pos) {
        if (rect == null) return;
        if (GridController.getNumRects() >= GridController.gridPointList.size()) return;

        if (pos >= 0) GridController.addRectangle(rect, pos);
        else GridController.addRectangle(rect);

        ViewGroup viewParent = (ViewGroup) rect.layout.getParent();
        if (viewParent != null) viewParent.removeView(rect.layout);

        layout.addView(rect.layout, new ViewGroup.LayoutParams(rect.getSize(), rect.getSize()));
        Log.v("cp", Integer.valueOf(GridController.getNumRects()).toString());
    }
}
