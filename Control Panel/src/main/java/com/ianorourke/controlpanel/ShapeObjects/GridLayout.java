package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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

    public void createRect() {
        if (GridController.getNumRects() >= GridController.gridPointList.size()) return;

        RectangleLayout rectLayout = new RectangleLayout(layout.getContext(), GridController.rectSize);

        GridController.addRectangle(rectLayout);

        addLayout(rectLayout);

        Log.v("cp", new Integer(GridController.getNumRects()).toString());
    }

    private void addLayout(RectangleLayout rect) {
        if (rect == null) return;

        ViewGroup viewParent = (ViewGroup) rect.layout.getParent();
        if (viewParent != null) viewParent.removeView(rect.layout);

        layout.addView(rect.layout, new ViewGroup.LayoutParams(rect.getSize(), rect.getSize()));
    }
}
