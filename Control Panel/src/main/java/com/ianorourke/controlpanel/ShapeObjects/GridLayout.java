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

    static final int NO_POS = -1;

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
                r = createInstrument(cls);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (r != null) addLayout(r, Integer.parseInt(values[1]));
        }
    }

    private RectangleLayout createInstrument(Class c) {
        RectangleLayout inst = null;

        try {
            inst = (RectangleLayout) c.getConstructor(new Class[]{Context.class, int.class}).newInstance(new Object[]{layout.getContext(), GridController.rectSize});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return inst;
    }

    public void addInstrument(Class c) {
        addLayout(createInstrument(c), NO_POS);
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
