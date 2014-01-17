package com.ianorourke.controlpanel.Orbiter;

import java.util.ArrayList;
import java.util.List;

public class OrbiterMessages {
    //Outgoing Messages
    private static List<String> messages = new ArrayList<String>();

    public static boolean hasMessages() {
        return !messages.isEmpty();
    }

    public static List<String> getMessages() {
        return messages;
    }

    public static void addMessage(String m) {
        if (m != null && !m.equals("")) messages.add(m);
    }

    public static void clearMessages() {
        messages.clear();
    }

    public static void setDebugMessage(String m) {
        addMessage("ORB:DebugString:" + m + "\r");
    }

    public static void clearDebugMessage() {
        addMessage("ORB:DebugString:CLEAR");
    }

    //Subscription Handles
    public static final String handleAltitude = "SHIP:FOCUS:Alt";
    public static final String handleAirspeed = "SHIP:FOCUS:Airspd";
    public static final String handleIndicatedAirspeed = "SHIP:FOCUS:IndSpd";
    public static final String handleOrbitSpeed = "SHIP:FOCUS:OrbSpd";
    public static final String handleGroundSpeed = "SHIP:FOCUS:GndSpd";
    public static final String handleVesselName = "SHIP:FOCUS:Name";
    public static final String handleFuelFlowRate = "SHIP:FOCUS:DfltFuelFlowRate";
    public static final String handleFuelMass = "SHIP:FOCUS:DfltFuelMass";
    public static final String handleFuelMaxMass = "SHIP:FOCUS:DfltMaxFuelMass";
    public static final String handleEngineStatus = "FOCUS:EngineStatus";
    public static final String handleAtmophericConditions = "SHIP:FOCUS:AtmConditions";
}
