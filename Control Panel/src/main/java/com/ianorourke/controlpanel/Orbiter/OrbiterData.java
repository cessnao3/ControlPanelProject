package com.ianorourke.controlpanel.Orbiter;

import com.ianorourke.controlpanel.ShapeObjects.GridController;

public class OrbiterData {
    public static String message = "FOCUS:Alt";

    public static String name;
    public static double altitude;

    public static String[] subscribeMessages = {"SUBSCRIBE:1:FOCUS:Alt", "SUBSCRIBE:1:FOCUS:Name"};

    //TODO: Parse Message

    public static void parseMessage(String message) {
        if (message.contains("FOCUS:Alt=")) altitude = new Double(message.replace("FOCUS:Alt=", "")).doubleValue();
    }
}
