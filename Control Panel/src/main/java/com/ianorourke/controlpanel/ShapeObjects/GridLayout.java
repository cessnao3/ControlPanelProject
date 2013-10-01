package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
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
        RectangleLayout rectLayout = this.mainView.createRect();

        if (rectLayout != null) {
            RectangleLayout.RectangleView rect = rectLayout.getRectangleView();

            layout.addView(rectLayout.layout, new ViewGroup.LayoutParams(rectLayout.getSize(), rectLayout.getSize()));
        }
    }

}
