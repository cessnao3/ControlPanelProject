package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.ShapeObjects.GridController;
import com.ianorourke.controlpanel.ShapeObjects.GridObject;

import android.content.Context;
import android.content.SharedPreferences;

public class OrbiterSave {
    private static final String GRID_SAVE_ID = "grid_save_id";
    private static final String GRID_SAVE_KEY = "grid_save_key";

    public static void save(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String save = "";

        for (int i = 0; i < GridController.gridPointList.size(); i++) {
            GridObject object = GridController.gridPointList.get(i);

            if (object.hasObject()) save += object.getObject().getClass() + "//\\\\" + String.valueOf(i) + "\n";
        }

        editor.putString(GRID_SAVE_KEY, save.trim());
        editor.commit();
    }

    public static String load(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(GRID_SAVE_KEY, null);
    }
}
