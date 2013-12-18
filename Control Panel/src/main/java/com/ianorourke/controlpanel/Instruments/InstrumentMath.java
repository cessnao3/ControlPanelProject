package com.ianorourke.controlpanel.Instruments;

import com.ianorourke.controlpanel.Orbiter.OrbiterData;

public class InstrumentMath {
    public static String getScientificNotation(Double d) {
        return String.format("%6.3e", d);
    }

    public static double getExponent(Double d) {
        String sci = getScientificNotation(d);

        if (!sci.contains("e")) return 0.0;
        sci = sci.substring(sci.indexOf("e")).replace("e", "");

        return Double.valueOf(sci).doubleValue();
    }

    public static double getInitialNumber(Double d) {
        String sci = getScientificNotation(d);
        sci = sci.replace(sci.substring(sci.indexOf("e")), "");

        return Double.valueOf(sci).doubleValue();
    }
}
