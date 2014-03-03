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

            //TODO: Fix Splitting
            values[1] = values[1].replace("\\", "").replace("/", "");

            RectangleLayout r = null;

            //TODO: Fix Loading
            /*
            if (values[0].equals(Altimeter.class.toString())) r = new Altimeter(layout.getContext(), GridController.rectSize);
            else if (values[0].equals(AttitudeMode.class.toString())) r = new AttitudeMode(layout.getContext(), GridController.rectSize);
            else if (values[0].equals(Machometer.class.toString())) r = new Machometer(layout.getContext(), GridController.rectSize);
            else if (values[0].equals(NameDisplay.class.toString())) r = new NameDisplay(layout.getContext(), GridController.rectSize);
            else if (values[0].equals(RemainingPropellent.class.toString())) r = new RemainingPropellent(layout.getContext(), GridController.rectSize);
            else if (values[0].equals(ToggleHud.class.toString())) r = new ToggleHud(layout.getContext(), GridController.rectSize);
            else if (values[0].equals(Velocity.class.toString())) r = new Velocity(layout.getContext(), GridController.rectSize);
            else if (values[0].equals(VerticalSpeed.class.toString())) r = new VerticalSpeed(layout.getContext(), GridController.rectSize);
            */

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

    private void addLayout(RectangleLayout rect, int pos) {
        if (rect == null) return;
        if (GridController.getNumRects() >= GridController.gridPointList.size()) return;

        GridController.addRectangle(rect, pos);

        ViewGroup viewParent = (ViewGroup) rect.layout.getParent();
        if (viewParent != null) viewParent.removeView(rect.layout);

        layout.addView(rect.layout, new ViewGroup.LayoutParams(rect.getSize(), rect.getSize()));
        Log.v("cp", Integer.valueOf(GridController.getNumRects()).toString());
    }
}
