package com.ianorourke.controlpanel.Orbiter;

import java.util.Map;

public class OrbiterStatus {
    //Atmospheric Conditions

    public static double temp = 0.0;
    public static double density = 0.0;
    public static double pressure = 0.0;
    public static double dynpressure = 0.0;
    public static double mach = 0.0;

    public static void resetAtmoContitions() {
        temp = 0.0;
        density = 0.0;
        pressure = 0.0;
        dynpressure = 0.0;
        mach = 0.0;
    }

    //Engine Status

    public static double main = 0.0;
    public static double hover = 0.0;
    public static int attMode = 0;

    public static void resetEngineStatus() {
        main = 0.0;
        hover = 0.0;
        attMode = 0;
    }

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

    public static void resetPropStatus() {
        propMass = 0.0;
        maxPropMass = 0.0;
        propFlowRate = 0.0;
    }

    //Vessel Status

    public static String name = "Ship";
    public static double altitude = 0.0;

    public static double airspeed = 0.0;
    public static double indicatedAirspeed = 0.0;
    public static double orbitSpeed = 0.0;
    public static double groundSpeed = 0.0;

    public static double[] airspeedVector = new double[3];

    public static void resetVesselStatus() {
        name = "Ship";
        altitude = 0.0;

        airspeed = 0.0;
        indicatedAirspeed = 0.0;
        orbitSpeed = 0.0;
        groundSpeed = 0.0;
    }

    //Update Orbiter Status

    public static void parseOrbiterStatus(Map<String, String> data) {
        //Atmospheric Conditions
        String atmoString = data.get(OrbiterMessages.handleAtmophericConditions);

        if (atmoString != null && !atmoString.equals("") && !atmoString.contains("ERR")) {
            String[] mA = atmoString.split(",");

            temp = parseDouble(mA[0]);
            density = parseDouble(mA[1]);
            pressure = parseDouble(mA[2]);
            dynpressure = parseDouble(mA[3]);
            mach = parseDouble(mA[4]);
        } else resetAtmoContitions();

        //Engine Status
        String engineString = data.get(OrbiterMessages.handleEngineStatus);
        if (engineString != null && !engineString.equals("") && !engineString.contains("ERR")) {
            String[] mE = engineString.split(",");

            main = parseDouble(mE[0]);
            hover = parseDouble(mE[1]);
            attMode = parseInt(mE[2]);
        } else resetEngineStatus();

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

        String aVectorString = data.get(OrbiterMessages.handleAirspeedVector);
        if (aVectorString != null && !aVectorString.equals("") && !aVectorString.contains("ERR")) {
            String[] mA = aVectorString.split(",");

            airspeedVector[0] = parseDouble(mA[0]);
            airspeedVector[1] = parseDouble(mA[2]);
            airspeedVector[2] = parseDouble(mA[1]);
        }
    }

    public static void resetOrbiterStatus() {
        resetAtmoContitions();
        resetEngineStatus();
        resetPropStatus();
        resetVesselStatus();
    }

    //Parse String Methods

    private static int parseInt(String s) {
        if (s == null || s.contains("ERR") || s.equals("")) return 0;

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
        if (s == null || s.contains("ERR") || s.equals("")) return 0.0;

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