package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ianorourke.controlpanel.Instruments.Altimeter;
import com.ianorourke.controlpanel.Instruments.NameDisplay;

public class GridLayout {
    private RelativeLayout layout;
    private GridView mainView;


    public GridLayout(Context context) {
        layout = new RelativeLayout(context);
        mainView = new GridView(context);

        layout.addView(mainView);

        for (int i = 0; i < GridController.gridPointList.size(); i++) {
            GridObject point = GridController.gridPointList.get(i);

            if (point.hasObject()) addLayout(point.getObject());
        }
    }

    public RelativeLayout getLayout() {
        return layout;
    }

    //Rect Types
    public void createRect() {
        RectangleLayout rectLayout = new RectangleLayout(layout.getContext(), GridController.rectSize);
        addLayout(rectLayout);
    }

    public void createAltimeter() {
        Altimeter alt = new Altimeter(layout.getContext(), GridController.rectSize);
        addLayout(alt);
    }

    public void createNameDisplay() {
        NameDisplay name = new NameDisplay(layout.getContext(), GridController.rectSize);
        addLayout(name);
    }

    //Layout Params
    private void addLayout(RectangleLayout rect) {
        if (rect == null) return;
        if (GridController.getNumRects() >= GridController.gridPointList.size()) return;

        GridController.addRectangle(rect);

        ViewGroup viewParent = (ViewGroup) rect.layout.getParent();
        if (viewParent != null) viewParent.removeView(rect.layout);

        layout.addView(rect.layout, new ViewGroup.LayoutParams(rect.getSize(), rect.getSize()));

        Log.v("cp", new Integer(GridController.getNumRects()).toString());
    }
}
