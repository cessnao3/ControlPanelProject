package com.ianorourke.controlpanel.Orbiter;

import java.util.Map;

import android.util.Log;

public class OrbiterStatus {
    //TODO: Move to Static Class

    //Atmospheric Conditions

    private static String[] mA = new String[5];

    public static double temp = 0.0;
    public static double density = 0.0;
    public static double pressure = 0.0;
    public static double dynpressure = 0.0;
    public static double mach = 0.0;

    //Engine Status

    private static String[] mE = new String[3];

    public static double main = 0.0;
    public static double hover = 0.0;
    public static int attMode = 0;

    //Fuel Status

    public static double propMass = 0.0;
    public static double maxPropMass = 0.0;
    public static double propFlowRate = 0.0;

    public static String getRemainingPropTime() {
        double secondsRemaining = 0.0;
        double propellentPercentage = 0.0;

        //PropRate in Kg/s

        if (propFlowRate > 0.0) secondsRemaining = propMass / propFlowRate;
        if (maxPropMass > 0.0) propellentPercentage = propMass / maxPropMass * 100.0;

        return ((secondsRemaining != 0.0) ? Double.valueOf(Math.round(secondsRemaining)).toString() : "NaN") + "\n" + Double.valueOf(Math.round(propellentPercentage)).toString();
    }

    //Vessel Status

    public static String name = "Ship";
    public static double altitude = 0.0;

    public static double airspeed = 0.0;
    public static double indicatedAirspeed = 0.0;
    public static double orbitSpeed = 0.0;
    public static double groundSpeed = 0.0;

    //Update Orbiter Status

    public static void parseOrbiterStatus(Map<String, String> data) {
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
        altitude = parseDouble(data.get(OrbiterMessages.handleAltitude));

        airspeed = parseDouble(data.get(OrbiterMessages.handleAirspeed));
        indicatedAirspeed = parseDouble(data.get(OrbiterMessages.handleIndicatedAirspeed));
        orbitSpeed = parseDouble(data.get(OrbiterMessages.handleOrbitSpeed));
        groundSpeed = parseDouble(data.get(OrbiterMessages.handleGroundSpeed));
    }

    public static void resetOrbiterStatus() {
        //Atmospheric Status

        temp = 0.0;
        density = 0.0;
        pressure = 0.0;
        dynpressure = 0.0;
        mach = 0.0;

        //Engine Status

        main = 0.0;
        hover = 0.0;
        attMode = 0;

        //Fuel Status

        propMass = 0.0;
        maxPropMass = 0.0;
        propFlowRate = 0.0;

        name = "Ship";
        altitude = 0.0;

        airspeed = 0.0;
        indicatedAirspeed = 0.0;
        orbitSpeed = 0.0;
        groundSpeed = 0.0;
    }

    //Parse String Methods

    private static int parseInt(String s) {
        int i;

        try {
            i = Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }

        return i;
    }

    private static double parseDouble(String s) {
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