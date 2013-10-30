package com.ianorourke.controlpanel.Orbiter;

import com.ianorourke.controlpanel.Orbiter.OrbiterDataTypes.*;
import com.ianorourke.controlpanel.ShapeObjects.GridController;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class OrbiterData {
    private static String frequency = "2";

    //TODO: TIMER!!!!

    //TODO: Clean Messages
    private static Map<String, String> subscriptionMap = new HashMap<String, String>();
    private static Map<String, String> messageMap = new HashMap<String, String>();

    //TODO: Move to Actual Thingy - NOT INDIVIDUAL FUNCTION
    public static void createMessages() {
        messageMap.put("SHIP:FOCUS:Alt", "");
        messageMap.put("SHIP:FOCUS:Name", "");
        messageMap.put("SHIP:FOCUS:DfltFuelFlowRate", "");
        messageMap.put("SHIP:FOCUS:DfltFuelMass", "");
        messageMap.put("SHIP:FOCUS:DfltMaxFuelMass", "");
        messageMap.put("FOCUS:EngineStatus", "");
        messageMap.put("SHIP:FOCUS:AtmConditions", "");
    }

    public static OrbiterVesselStatus vessel = new OrbiterVesselStatus();
    public static OrbiterEngineStatus engine = new OrbiterEngineStatus();
    public static OrbiterFuelStatus fuel = new OrbiterFuelStatus();
    public static OrbiterAtmosphericConditions atmCond = new OrbiterAtmosphericConditions();

    public static void parseMessage(String message) {
        //TODO: Remove
        if (messageMap.isEmpty()) createMessages();

        if (subscriptionMap == null) return;
        if (message == null || !message.contains("=")) return;

        Log.v("cp", "Message: " + message);

        if (message.contains("SUBSCRIBE")) {
            String id = message.substring(message.indexOf("=")).replace("=", "");
            String call = message.substring(0, message.indexOf("=")).replace("SUBSCRIBE:", "");

            if (id != null && !id.contains("ERR") && !subscriptionMap.containsKey(id)) {
                if (call != null && !call.equals("")) subscriptionMap.put(id, call);
            }

            return;
        }

        String key = message.substring(0, message.indexOf("="));
        if (key == null || key.equals("")) return;

        String action = subscriptionMap.get(key);
        if (action == null || action.equals("")) return;

        String responseString = message.replace(key + "=", "");

        String messageKey = messageMap.get(key);
        if (messageMap.containsKey(messageKey)) messageMap.put(messageKey, responseString);

        //Move UpdateRects call to Timer
        GridController.updateRects();
    }

    //Data Actions
    public static Map<String, String> getDataMap() {
        return messageMap;
    }

    //Subscription Actions
    public static void clearSubscriptionMap() {
        subscriptionMap.clear();
    }

    public static String[] getSubscriptions() {
        String[] finalSubscriptions = new String[messageMap.size()];

        for (int i = 0; i < finalSubscriptions.length; i++) {
            finalSubscriptions[i] = messageMap.keySet().toArray()[i].toString();
        }

        return finalSubscriptions;
    }
}