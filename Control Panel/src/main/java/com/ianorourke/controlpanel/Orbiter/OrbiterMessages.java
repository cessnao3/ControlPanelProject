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
