package fr.graynaud.eu4saveconverter.service.object.save;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum SubjectType {
    COLONY("colony"),
    MARCH("march"),
    VASSAL("vassal"),
    DAIMYO("daimyo_vassal"),
    TRIBUTARY("tributary_state"),
    CLIENT("client_vassal"),
    PERSONAL_UNION("personal_union");

    public final String value;

    private static final Map<String, SubjectType> values = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(subjectType -> values.put(subjectType.value, subjectType));
    }

    SubjectType(String value) {
        this.value = value;
    }

    public static SubjectType getByValue(String value) {
        if (value == null) {
            return null;
        }

        return values.getOrDefault(value, null);
    }
}
