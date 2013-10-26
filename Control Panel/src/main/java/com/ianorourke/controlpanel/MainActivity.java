package com.ianorourke.controlpanel;

import com.ianorourke.controlpanel.Orbiter.OrbiterConnect;
import com.ianorourke.controlpanel.ShapeObjects.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;

public class MainActivity extends Activity {
    private String labelText = "Hello, World!";

    private GridLayout mainLayout;

    private OrbiterConnect orbiterConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.mainLayout == null || this.mainLayout.getLayout() == null) {
            this.mainLayout = new GridLayout(this);

            setContentView(this.mainLayout.getLayout());
        }

        if (orbiterConnect == null) orbiterConnect = new OrbiterConnect();

        GridController.resetAllObjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_alt:
                this.mainLayout.createAltimeter();
                return true;
            case R.id.menu_name:
                this.mainLayout.createNameDisplay();
                return true;
            case R.id.menu_connect:
                orbiterConnect.connect();
                return true;
            case R.id.menu_change_hud:
                this.mainLayout.createToggleHud();
                return true;
            case R.id.menu_toggle_editing:
                GridController.isEditing = (GridController.isEditing) ? false : true;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}