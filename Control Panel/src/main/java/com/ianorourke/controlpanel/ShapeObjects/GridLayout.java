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
    }

    public RelativeLayout getLayout() {
        return layout;
    }

    public void createRect() {
        if (GridController.getNumRects() >= GridController.gridPointList.size() - 1) return;

        RectangleLayout rectLayout = new RectangleLayout(layout.getContext(), GridController.rectSize);

        GridController.addRectangle(rectLayout);

        if (rectLayout != null) {
            layout.addView(rectLayout.layout, new ViewGroup.LayoutParams(rectLayout.getSize(), rectLayout.getSize()));
        }

        Log.v("cp", new Integer(GridController.getNumRects()).toString());
    }

}
