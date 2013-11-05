package com.ianorourke.controlpanel.Orbiter.OrbiterDataTypes;

import android.util.Log;

public class OrbiterAtmosphericConditions {
    public String[] m = new String[5];

    public double temp = 0.0;
    public double density = 0.0;
    public double pressure = 0.0;
    public double dynpressure = 0.0;
    public double mach = 0.0;

    public void parseAtmosphericConditions(String in) {
        if (in.contains("ERR")) return;

        if (in == null || in.equals("")) return;

        m = in.split(",");

        temp = Double.valueOf(m[0]).doubleValue();
        density = Double.valueOf(m[1]).doubleValue();
        pressure = Double.valueOf(m[2]).doubleValue();
        dynpressure = Double.valueOf(m[3]).doubleValue();
        mach = Double.valueOf(m[4]).doubleValue();
    }
}