package com.ianorourke.controlpanel.Orbiter;

import com.ianorourke.controlpanel.Orbiter.OrbiterDataTypes.*;
import com.ianorourke.controlpanel.ShapeObjects.GridController;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class OrbiterData {
    private static int frequency = 2;

    //TODO: Fix Connection Data Updating

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

    public static OrbiterVesselStatus vessel = new OrbiterVesselStatus();
    public static OrbiterEngineStatus engine = new OrbiterEngineStatus();
    public static OrbiterFuelStatus fuel = new OrbiterFuelStatus();
    public static OrbiterAtmosphericConditions atmCond = new OrbiterAtmosphericConditions();

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

            if (id != null && !id.contains("ERR") && !subscriptionMap.containsKey(id)) {
                if (call != null && !call.equals("")) subscriptionMap.put(id, call);
            }

            return;
        }

        String key = message.substring(0, message.indexOf("="));

        Log.v("cp", "Key: " + key);

        if (key == null || key.equals("")) return;

        String action = subscriptionMap.get(key);
        if (action == null || action.equals("")) return;

        String responseString = message.replace(key + "=", "");

        String messageKey = subscriptionMap.get(key);
        if (messageMap.containsKey(messageKey)) messageMap.put(messageKey, responseString);

        //TODO: Move to Timer
        updateData();
    }

    public static void updateData() {
        atmCond.parseAtmosphericConditions(messageMap.get(OrbiterMessages.handleAtmophericConditions));
        engine.parseEngineStatus(messageMap.get(OrbiterMessages.handleEngineStatus));
        fuel.updateValues(messageMap);
        vessel.update(messageMap);

        GridController.updateRects();
    }

    public static void resetData() {
        atmCond = new OrbiterAtmosphericConditions();
        engine = new OrbiterEngineStatus();
        fuel = new OrbiterFuelStatus();
        vessel = new OrbiterVesselStatus();
    }

    //Subscription Actions
    public static Map<String, String> getSubscriptionMap() {
        return subscriptionMap;
    }

    public static void clearSubscriptionMap() {
        subscriptionMap.clear();
    }

    public static String[] getSubscriptions() {
        String[] finalSubscriptions = new String[messageMap.size()];

        for (int i = 0; i < finalSubscriptions.length; i++) {
            finalSubscriptions[i] = "SUBSCRIBE:" + Integer.valueOf(frequency).toString() + ":" + messageMap.keySet().toArray()[i].toString();
        }

        return finalSubscriptions;
    }
}