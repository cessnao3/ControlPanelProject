package com.ianorourke.controlpanel.Orbiter;

import com.ianorourke.controlpanel.ShapeObjects.GridController;

import java.util.HashMap;
import java.util.Map;

public class OrbiterData {
    private static String message = "";

    public static String name;
    public static double altitude;

    public static String[] subscribeMessages = {"SUBSCRIBE:1:FOCUS:Alt", "SUBSCRIBE:1:FOCUS:Name"};

    private static Map<String, String> subscriptionMap = new HashMap<String, String>();

    //TODO: Parse Message
    public static void parseMessage(String message) {
        if (subscriptionMap == null) return;

        //TODO: More Elegant Solution

        if (subscribeMessages.length > subscriptionMap.size()) {
            for (int i = 0; i < subscribeMessages.length; i++) {
                if (message.contains(subscribeMessages[i] + "=")) {
                    String key = message.replace(subscribeMessages[i] + "=", "");

                    if (subscriptionMap.get(key) == null) subscriptionMap.put(key, subscribeMessages[i]);
                }
            }
        }



        //if (message.contains("FOCUS:Alt=")) altitude = new Double(message.replace("FOCUS:Alt=", "")).doubleValue();
    }

    public static Map<String, String> getSubscriptionMap() {
        return subscriptionMap;
    }

    public static void setMessage(String m) {
        if (m == null) m = "";

        message = m;
    }

    public static String getMessage() {
        return message;
    }
}