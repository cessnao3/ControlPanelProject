package com.ianorourke.controlpanel.Orbiter;

import java.util.Map;

public class OrbiterStatus {
    //Atmospheric Conditions

    public String[] mA = new String[5];

    public double temp = 0.0;
    public double density = 0.0;
    public double pressure = 0.0;
    public double dynpressure = 0.0;
    public double mach = 0.0;

    //Engine Status

    public String[] mE = new String[3];

    public double main = 0.0;
    public double hover = 0.0;
    public int attMode = 0;

    //Fuel Status

    public double propMass = 0.0;
    public double maxPropMass = 0.0;
    public double propFlowRate = 0.0;

    public String getRemainingPropTime() {
        double secondsRemaining = 0.0;
        double propellentPercentage = 0.0;

        //PropRate in Kg/s

        if (propFlowRate > 0.0) secondsRemaining = propMass / propFlowRate;
        if (maxPropMass > 0.0) propellentPercentage = propMass / maxPropMass * 100.0;

        return ((secondsRemaining != 0.0) ? Double.valueOf(Math.round(secondsRemaining)).toString() : "NaN") + "\n" + Double.valueOf(Math.round(propellentPercentage)).toString();
    }

    //Vessel Status

    public String name = "Ship";
    public double airspeed = 0.0;
    public double altitude = 0.0;

    //Update Orbiter Status

    public void parseOrbiterStatus(Map<String, String> data) {
        //Atmospheric Conditions
        String atmoString = data.get(OrbiterMessages.handleAtmophericConditions);
        if (atmoString.contains("ERR")) return;

        if (atmoString == null || atmoString.equals("")) return;

        mA = atmoString.split(",");

        temp = parseDouble(mA[0]);
        density = parseDouble(mA[1]);
        pressure = parseDouble(mA[2]);
        dynpressure = parseDouble(mA[3]);
        mach = parseDouble(mA[4]);

        //Engine Status
        String engineString = data.get(OrbiterMessages.handleEngineStatus);
        if (engineString == null || engineString.equals("")) return;

        mE = engineString.split(",");

        main = parseDouble(mE[0]);
        hover = parseDouble(mE[1]);
        attMode = parseInt(mE[2]);

        //Propellant Data
        propMass = parseDouble(data.get(OrbiterMessages.handleFuelMass));
        maxPropMass = parseDouble(data.get(OrbiterMessages.handleFuelMaxMass));
        propFlowRate = parseDouble(data.get(OrbiterMessages.handleFuelFlowRate));

        //Vessel Status
        name = data.get(OrbiterMessages.handleVesselName);

        airspeed = parseDouble(data.get(OrbiterMessages.handleAirspeed));
        altitude = parseDouble(data.get(OrbiterMessages.handleAltitude));
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