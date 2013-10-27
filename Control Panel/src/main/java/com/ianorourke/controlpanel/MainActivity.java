package com.ianorourke.controlpanel;

import android.content.DialogInterface;
import com.ianorourke.controlpanel.Orbiter.OrbiterConnect;
import com.ianorourke.controlpanel.ShapeObjects.*;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.*;

public class MainActivity extends Activity {
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
            case R.id.menu_new:
                final CharSequence[] messages = {"Altimeter", "Name", "Toggle HUD Color"};

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
                                mainLayout.createNameDisplay();
                                break;
                            case 2:
                                mainLayout.createToggleHud();
                                break;
                            default:
                                break;
                        }

                        //dialog.dismiss();
                    }
                }).show();
                return true;
            case R.id.menu_connect:
                orbiterConnect.connect();
                return true;
            case R.id.menu_toggle_editing:
                GridController.isEditing = (!GridController.isEditing);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}