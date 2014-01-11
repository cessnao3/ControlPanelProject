package com.ianorourke.controlpanel.Orbiter;

import com.ianorourke.controlpanel.ShapeObjects.GridController;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class OrbiterData {
    public static int frequency = 2;

    private static Map<String, String> subscriptionMap = new HashMap<String, String>();
    private static Map<String, String> messageMap = new HashMap<String, String>() {{
        put(OrbiterMessages.handleAltitude, "");
        put(OrbiterMessages.handleAirspeed, "");
        put(OrbiterMessages.handleVesselName, "");
        put(OrbiterMessages.handleFuelFlowRate, "");
        put(OrbiterMessages.handleFuelMass, "");
        put(OrbiterMessages.handleFuelMaxMass, "");
        put(OrbiterMessages.handleEngineStatus, "");
        put(OrbiterMessages.handleAtmophericConditions, "");
    }};

    public static OrbiterStatus status = new OrbiterStatus();

    public static void parseMessage(String message) {
        //TODO: Clean ParseMessage

        if (subscriptionMap == null) return;
        if (message == null || !message.contains("=")) return;

        //Log.v("cp", "Message: " + message);

        if (message.contains("SUBSCRIBE")) {
            String id = message.substring(message.indexOf("=")).replace("=", "");
            String call = message;
            call = call.replace("SUBSCRIBE:" + Integer.valueOf(frequency).toString() + ":", "");
            call = call.substring(0, call.indexOf("="));

            Log.v("cp", id + ":" + call);

            if (!id.contains("ERR") && !subscriptionMap.containsKey(id) && !call.equals("")) subscriptionMap.put(id, call);

            return;
        }

        String key = message.substring(0, message.indexOf("="));

        if (key == null || key.equals("")) return;

        String responseString = message.replace(key + "=", "");

        String messageKey = subscriptionMap.get(key);
        if (messageMap.containsKey(messageKey)) messageMap.put(messageKey, responseString);

        //TODO: Move Somewhere
        updateData();
    }

    public static void updateData() {
        status.parseOrbiterStatus(messageMap);

        GridController.updateRects();
    }

    public static void resetData() {
        status = new OrbiterStatus();
    }

    //Subscription Actions
    /*
    public static Map<String, String> getSubscriptionMap() {
        return subscriptionMap;
    }
    */

    public static String[] getSubscriptionIds() {
        int count = subscriptionMap.size();

        String[] ids = new String[count];

        int i = 0;

        for (String key : subscriptionMap.keySet()) {
            ids[i] = key;
            i++;
        }

        return ids;
    }

    public static void clearSubscriptionMap() {
        subscriptionMap.clear();
    }

    public static String[] getSubscriptions() {
        Log.v("cp", "Message Map Size: " + Integer.valueOf(messageMap.size()).toString());

        String[] finalSubscriptions = new String[messageMap.size()];

        for (int i = 0; i < finalSubscriptions.length; i++) {
            finalSubscriptions[i] = "SUBSCRIBE:" + Integer.valueOf(frequency).toString() + ":" + messageMap.keySet().toArray()[i].toString();
        }

        return finalSubscriptions;
    }
}