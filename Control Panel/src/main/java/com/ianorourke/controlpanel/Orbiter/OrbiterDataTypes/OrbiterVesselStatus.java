package com.ianorourke.controlpanel.Orbiter.OrbiterDataTypes;

import com.ianorourke.controlpanel.Orbiter.OrbiterData;
import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;

import java.util.Map;

public class OrbiterVesselStatus {
    public String name = "Ship";

    public double airspeed = 0.0;
    public double altitude = 0.0;

    public void update(Map<String, String> data) {
        name = data.get(OrbiterMessages.handleVesselName);

        updateAirspeed(data.get(OrbiterMessages.handleAirspeed));
        updateAltitude(data.get(OrbiterMessages.handleAltitude));
    }

    private void updateAirspeed(String s) {
        if (s != null && !s.equals("") && !s.contains("ERR")) airspeed = Double.valueOf(s).doubleValue();
    }

    private void updateAltitude(String s) {
        if (s != null && !s.equals("") && !s.contains("ERR")) altitude = Double.valueOf(s).doubleValue();
    }
}