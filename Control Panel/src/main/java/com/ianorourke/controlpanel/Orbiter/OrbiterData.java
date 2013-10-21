package com.ianorourke.controlpanel.Orbiter;

public class OrbiterData {
    public static String message = "FOCUS:Alt";

    public static String name;
    public static double altitude;

    //TODO: Parse Message

    public static void parseMessage(String message) {
        if (message.contains("FOCUS:Alt=")) altitude = new Double(message.replace("FOCUS:Alt=", "")).doubleValue();
    }
}
