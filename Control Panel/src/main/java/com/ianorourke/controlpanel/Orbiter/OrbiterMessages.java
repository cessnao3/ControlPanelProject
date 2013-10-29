package com.ianorourke.controlpanel.Orbiter;

import java.util.ArrayList;
import java.util.List;

public class OrbiterMessages {
    private static List<String> messages = new ArrayList<String>();

    //Connection Protocols

    //TODO: Graphics for Instruments

    public static String getAltitudeHandle() {
        return "SHIP:FOCUS:Alt";
    }

    public static String getNameHandle() {
        return "SHIP:FOCUS:Name";
    }

    public static String getPropFlowHandle() {
        return "SHIP:FOCUS:DfltFuelFlowRate";
    }

    public static String getPropMassHandle() {
        return "SHIP:FOCUS:DfltFuelMass";
    }

    public static String getPropMaxMass() {
        return "SHIP:FOCUS:DfltMaxFuelMass";
    }

    public static String getEngineStatusHandle() {
        return "FOCUS:EngineStatus";
    }

    //TODO: Velocities
    //TODO: Attitude
    //TODO: Other Buttons

    //Messages

    public static boolean hasMessages() {
        return messages.size() > 0;
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
}
