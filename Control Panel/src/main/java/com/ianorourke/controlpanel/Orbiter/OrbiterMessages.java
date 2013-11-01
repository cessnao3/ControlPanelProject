package com.ianorourke.controlpanel.Orbiter;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

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

    //Subscription Handles
    public static final String handleAltitude = "SHIP:FOCUS:Alt";
    public static final String handleVesselName = "SHIP:FOCUS:Name";
    public static final String handleFuelFlowRate = "SHIP:FOCUS:DfltFuelFlowRate";
    public static final String handleFuelMass = "SHIP:FOCUS:DfltFuelMass";
    public static final String handleFuelMaxMass = "SHIP:FOCUS:DfltMaxFuelMass";
    public static final String handleEngineStatus = "FOCUS:EngineStatus";
    public static final String handleAtmophericConditions = "SHIP:FOCUS:AtmConditions";
}
