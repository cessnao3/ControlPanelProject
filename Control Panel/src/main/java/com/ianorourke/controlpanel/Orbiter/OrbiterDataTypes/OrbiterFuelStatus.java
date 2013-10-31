package com.ianorourke.controlpanel.Orbiter.OrbiterDataTypes;

import com.ianorourke.controlpanel.Orbiter.OrbiterData;

import java.util.Map;

public class OrbiterFuelStatus {
    public double propMass = 0.0;
    public double maxPropMass = 0.0;
    public double propFlowRate = 0.0;

    public void updateValues() {
        //TODO: Add Arguemnts for Update

        Map<String, String> data = OrbiterData.getDataMap();

        propMass = Double.valueOf(data.get("SHIP:FOCUS:DfltFuelMass")).doubleValue();
        maxPropMass = Double.valueOf(data.get("SHIP:FOCUS:DfltMaxFuelMass")).doubleValue();
        propFlowRate = Double.valueOf(data.get("SHIP:FOCUS:DfltFuelFlowRate")).doubleValue();
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
