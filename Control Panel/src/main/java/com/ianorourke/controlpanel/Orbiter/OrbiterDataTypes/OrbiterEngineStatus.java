package com.ianorourke.controlpanel.Orbiter.OrbiterDataTypes;

public class OrbiterEngineStatus {
    public String[] m = new String[3];

    public double main = 0.0;
    public double hover = 0.0;
    public int attMode = 0;

    public void parseEngineStatus(String in) {
        if (in == null || in.equals("")) return;

        m = in.split(",");

        main = Double.valueOf(m[0]).doubleValue();
        hover = Double.valueOf(m[1]).doubleValue();
        attMode = Integer.valueOf(m[2]).intValue();
    }
}
