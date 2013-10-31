package com.ianorourke.controlpanel.Orbiter.OrbiterDataTypes;

import com.ianorourke.controlpanel.Orbiter.OrbiterData;

import java.util.Map;

public class OrbiterVesselStatus {
    public String name = "Ship";

    public double altitude = 0.0;

    public void update() {
        //TODO: Add Arguemnts for Update

        Map<String, String> data = OrbiterData.getDataMap();

        name = data.get("SHIP:FOCUS:Name");
        altitude = Double.valueOf(data.get("SHIP:FOCUS:Name")).doubleValue();
    }
}
