package com.ianorourke.controlpanel.Orbiter;

import java.util.ArrayList;
import java.util.List;

public class OrbiterMessages {
    private static List<String> messages = new ArrayList<String>();

    //Connection Protocols

    public static String getAltitudeHandle() {
        return "SHIP:FOCUS:Alt";
    }

    public static String getNameHandle() {
        return "SHIP:FOCUS:Name";
    }

    //Messages

    public static boolean hasMessages() {
        if (messages.size() > 0) return true;
        else return false;
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
