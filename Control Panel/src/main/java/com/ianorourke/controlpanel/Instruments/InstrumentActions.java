package com.ianorourke.controlpanel.Instruments;

import java.util.ArrayList;
import java.util.List;

public class InstrumentActions {
    public static String getScientificNotation(Double d) {
        return String.format("%6.3e", d);
    }
    private static List<Class> instruments = new ArrayList<Class>() {
        {
            add(Altimeter.class);
            add(AttitudeMode.class);
            add(Machometer.class);
            add(NameDisplay.class);
            add(RemainingPropellent.class);
            add(ToggleHud.class);
            add(Velocity.class);
            add(VerticalSpeed.class);
        }
    };

    private static String[] instrumentNames = null;

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

    public static List<Class> getInstrumentList() {
        return instruments;
    }

    public static String[] getInstrumentNames() {
        if (instrumentNames == null) {
            instrumentNames = new String[instruments.size()];
            for (int i = 0; i < instrumentNames.length; i++) instrumentNames[i] = instruments.get(i).getSimpleName().replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
        }

        return instrumentNames;
    }
}
