package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.ShapeObjects.GridController;
import com.ianorourke.controlpanel.ShapeObjects.GridObject;

import android.content.Context;
import android.content.SharedPreferences;

public class OrbiterSave {
    private static final String GRID_SAVE_ID = "grid_save_id";

    public static void save(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        for (GridObject object : GridController.gridPointList) {
            if (object.hasObject()) {
                //TODO: FINISH SAVING
            }
        }

        editor.commit();
    }
}
