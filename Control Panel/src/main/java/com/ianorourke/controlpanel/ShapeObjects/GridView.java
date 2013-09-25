package com.ianorourke.controlpanel.ShapeObjects;

import android.content.Context;
import android.view.View;

import android.util.Log;

public class GridView extends View {
    public GridView(Context context) {
        super(context);

        this.setPadding(0, 0, 0, 0);
    }

    //public static int currentRect = 0;

    public void createRectangle() {
        //TODO: Re-add Limits?
        //if (currentRect >= gridPointList.size() - 1)

        RectangleView rect = new RectangleView(this.getContext(), Grid.rectSize);

        rect.setX(0);
        rect.setY(0);

        Grid.currentRect++;
        rect.color = Grid.currentRect;

        Log.v("cp", new Integer(Grid.currentRect).toString());

        //rectangleList.add(rect);

        Grid.addRectangle(rect);
    }
}
