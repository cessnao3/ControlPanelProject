package com.ianorourke.controlpanel.Orbiter;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class OrbiterMessages {
    private static List<String> messages = new ArrayList<String>();

    public static boolean hasMessages() {
        return !messages.isEmpty();
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
