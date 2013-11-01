package com.ianorourke.controlpanel.Orbiter.OrbiterDataTypes;

import com.ianorourke.controlpanel.Orbiter.OrbiterData;
import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;

import java.util.Map;

public class OrbiterVesselStatus {
    public String name = "Ship";

    public double altitude = 0.0;

    public void update(Map<String, String> data) {
        name = data.get(OrbiterMessages.handleVesselName);

        if (!data.get(OrbiterMessages.handleAltitude).equals("")) altitude = Double.valueOf(data.get(OrbiterMessages.handleAltitude)).doubleValue();
    }
}
