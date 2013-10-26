package com.ianorourke.controlpanel.Orbiter;

import com.ianorourke.controlpanel.ShapeObjects.GridController;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class OrbiterData {
    private static String frequency = "1";

    private static String vesselName;
    private static double altitude;

    private static String[] subscribeMessages = {OrbiterMessages.getAltitudeHandle(), OrbiterMessages.getNameHandle()};

    private static Map<String, String> subscriptionMap = new HashMap<String, String>();

    public static void parseMessage(String message) {
        if (subscriptionMap == null) return;
        if (message == null || !message.contains("=")) return;

        Log.v("cp", "Message: " + message);

        if (message.contains("SUBSCRIBE")) {
            String id = message.substring(message.indexOf("=")).replace("=", "");
            if (id != null && !id.contains("ERR") && !subscriptionMap.containsKey(id)) subscriptionMap.put(id, message.replace("=" + id, ""));
            return;
        }

        String key = message.substring(0, message.indexOf("="));
        if (key == null || key.equals("")) return;

        String action = subscriptionMap.get(key);

        if (action == null || action.equals("")) return;

        if (action.contains(OrbiterMessages.getAltitudeHandle())) {
            altitude = new Double(message.replace(key + "=", "")).doubleValue();
        } else if (action.contains(OrbiterMessages.getNameHandle())) {
            vesselName = message.replace(key + "=", "");
        }

        GridController.updateRects();
    }

    //Subscription Actions
    public static Map<String, String> getSubscriptionMap() {
        return subscriptionMap;
    }

    public static void clearSubscriptionMap() {
        subscriptionMap.clear();
    }

    public static String[] getSubscriptions() {
        String[] finalSubscriptions = new String[subscribeMessages.length];

        for (int i = 0; i < finalSubscriptions.length; i++) {
            finalSubscriptions[i] = "SUBSCRIBE:" + frequency + ":" + subscribeMessages[i];
        }

        return finalSubscriptions;
    }

    //Data Retrieval
    public static String getAltitudeString() {
        return new Double(altitude).toString();
    }
    public static String getNameString() {
        return vesselName;
    }
}