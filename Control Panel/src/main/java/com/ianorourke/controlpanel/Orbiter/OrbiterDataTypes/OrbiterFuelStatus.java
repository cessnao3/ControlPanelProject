package com.ianorourke.controlpanel.Orbiter.OrbiterDataTypes;

import com.ianorourke.controlpanel.Orbiter.OrbiterData;
import com.ianorourke.controlpanel.Orbiter.OrbiterMessages;

import java.util.Map;

public class OrbiterFuelStatus {
    public double propMass = 0.0;
    public double maxPropMass = 0.0;
    public double propFlowRate = 0.0;

    public void updateValues(Map<String, String> data) {
        if (!data.get(OrbiterMessages.handleFuelMass).equals("")) propMass = Double.valueOf(data.get(OrbiterMessages.handleFuelMass)).doubleValue();
        if (!data.get(OrbiterMessages.handleFuelMaxMass).equals("")) maxPropMass = Double.valueOf(data.get(OrbiterMessages.handleFuelMaxMass)).doubleValue();
        if (!data.get(OrbiterMessages.handleFuelFlowRate).equals("")) propFlowRate = Double.valueOf(data.get(OrbiterMessages.handleFuelFlowRate)).doubleValue();
    }

    public String getRemainingPropTime() {
        double secondsRemaining = 0.0;
        double propellentPercentage = 0.0;

        //PropRate in Kg/s

        if (propFlowRate > 0.0) secondsRemaining = propMass / propFlowRate;
        if (maxPropMass > 0.0) propellentPercentage = propMass / maxPropMass * 100.0;

        return ((secondsRemaining != 0.0) ? Double.valueOf(Math.round(secondsRemaining)).toString() : "NaN") + "\n" + Double.valueOf(Math.round(propellentPercentage)).toString();
    }
}