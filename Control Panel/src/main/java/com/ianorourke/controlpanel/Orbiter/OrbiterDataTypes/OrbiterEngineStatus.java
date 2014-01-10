package com.ianorourke.controlpanel.Orbiter.OrbiterDataTypes;

public class OrbiterEngineStatus {
    public String[] m = new String[3];

    public double main = 0.0;
    public double hover = 0.0;
    public int attMode = 0;

    //TODO: Consolidate all Orbiter Data Types

    public void parseEngineStatus(String in) {
        if (in == null || in.equals("")) return;

        m = in.split(",");

        main = parseDouble(m[0]);
        hover = parseDouble(m[1]);
        attMode = parseInt(m[2]);
    }

    //Parse String Methods

    private int parseInt(String s) {
        int i;

        try {
            i = Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }

        return i;
    }

    private double parseDouble(String s) {
        double d;

        try {
            d = Double.parseDouble(s);
        } catch (Exception e) {
            e.printStackTrace();

            return 0.0;
        }

        return d;
    }
}