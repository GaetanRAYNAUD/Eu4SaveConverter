package fr.graynaud.eu4saveconverter.common;

import java.util.Map;

public final class ColorsUtils {

    private ColorsUtils() {}

    private static final Map<Integer, Color> customFlagsColors;

    static {
        customFlagsColors = Map.ofEntries(
                Map.entry(0, new Color(255, 255, 255)),
                Map.entry(1, new Color(20, 20, 20)),
                Map.entry(2, new Color(117, 38, 143)),
                Map.entry(3, new Color(113, 11, 43)),
                Map.entry(4, new Color(97, 12, 12)),
                Map.entry(5, new Color(175, 15, 15)),
                Map.entry(6, new Color(188, 90, 27)),
                Map.entry(7, new Color(64, 40, 22)),
                Map.entry(8, new Color(244, 184, 12)),
                Map.entry(9, new Color(17, 53, 13)),
                Map.entry(10, new Color(46, 114, 55)),
                Map.entry(11, new Color(18, 179, 113)),
                Map.entry(12, new Color(50, 173, 192)),
                Map.entry(13, new Color(30, 30, 128)),
                Map.entry(14, new Color(116, 198, 140)),
                Map.entry(15, new Color(0, 68, 131)),
                Map.entry(16, new Color(200, 48, 40))
                                         );
    }
}
