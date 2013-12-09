package com.ianorourke.controlpanel;

import android.content.DialogInterface;
import com.ianorourke.controlpanel.Orbiter.OrbiterConnect;
import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;
import com.ianorourke.controlpanel.ShapeObjects.*;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.*;

import android.widget.Toast;

public class MainActivity extends Activity {
    private GridLayout mainLayout;

    private OrbiterConnect orbiterConnect;

    private SharedPreferences mainPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Reconnect orbiterConnect to Activity after Orientation Change

        super.onCreate(savedInstanceState);

        this.mainPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (this.mainLayout == null || this.mainLayout.getLayout() == null) {
            this.mainLayout = new GridLayout(this);

            setContentView(this.mainLayout.getLayout());
        }

        if (orbiterConnect == null) orbiterConnect = new OrbiterConnect();

        GridController.deleteAllRects();
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
            case R.id.menu_new:
                final CharSequence[] messages = {"Altimeter", "Airspeed", "Name", "Toggle HUD Color", "Remaining Propellent", "Attitude Mode"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Instrument Selection");

                builder.setItems(messages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mainLayout.createAltimeter();
                                break;
                            case 1:
                                mainLayout.createAirspeed();
                                break;
                            case 2:
                                mainLayout.createNameDisplay();
                                break;
                            case 3:
                                mainLayout.createToggleHud();
                                break;
                            case 4:
                                mainLayout.createPropFlow();
                                break;
                            case 5:
                                mainLayout.createAttitudeMode();
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
                return true;
            case R.id.menu_connect:
                String ip = this.mainPreferences.getString("server_ip", "");

                String portString = this.mainPreferences.getString("server_port", "");
                int port = (!portString.equals("")) ? Integer.valueOf(portString).intValue() : 0;

                if (port == 0) orbiterConnect.connect(ip);
                else orbiterConnect.connect(ip, port);

                return true;
            case R.id.menu_reset_instruments:
                GridController.deleteAllRects();
                orbiterConnect.disconnect();
                orbiterConnect = new OrbiterConnect();
                return true;
            case R.id.menu_toggle_editing:
                GridController.isEditing = !GridController.isEditing;

                if (GridController.isEditing) item.setTitle("Stop Editing");
                else item.setTitle("Edit");
                return true;
            case R.id.menu_settings:
                Intent intent = new Intent(this, MainPreferencesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}