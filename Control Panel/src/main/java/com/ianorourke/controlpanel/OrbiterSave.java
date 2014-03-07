package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.ShapeObjects.GridController;
import com.ianorourke.controlpanel.ShapeObjects.GridObject;

import android.content.Context;
import android.content.SharedPreferences;

//TODO: Serialize Class Object?
//http://stackoverflow.com/questions/8887197/reliably-convert-any-object-to-string-and-then-back-again

public class OrbiterSave {
    //private static final String GRID_SAVE_ID = "grid_save_id";
    private static final String GRID_SAVE_KEY = "grid_save_key";
    public static final String GRID_SPLIT = ",";

    //TODO: Is Needed?
    public static boolean saveExists(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.contains(GRID_SAVE_KEY);
    }

    public static void save(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String save = "";

        for (int i = 0; i < GridController.gridPointList.size(); i++) {
            GridObject object = GridController.gridPointList.get(i);

            if (object.hasObject()) save += object.getObject().getClass().getName() + GRID_SPLIT + String.valueOf(i) + "\n";
        }

        editor.putString(GRID_SAVE_KEY, save.trim());
        editor.commit();
    }

    public static String load(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String loadString = sharedPref.getString(GRID_SAVE_KEY, null);

        if (loadString != null && !loadString.equals("")) GridController.deleteAllRects();
        else return null;

        GridController.isEditing = false;

        return loadString;
    }
}
