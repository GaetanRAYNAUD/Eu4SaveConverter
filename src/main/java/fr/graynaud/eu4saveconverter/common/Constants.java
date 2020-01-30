package fr.graynaud.eu4saveconverter.common;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public final class Constants {

    private Constants() {
    }

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.M.d");
    public static final String STARTING_TEXT = "EU4txt";
    public static final String DEFAULT_START_DATE = "1444.11.11";
    public static final String MARCH_DEPENDENCY_TYPE = "march";
    public static final List<String> EAST_SLAVIC_CULTURES = Arrays.asList("russian", "novgorodian", "ryazanian", "byelorussian", "ruthenian", "karelian");
}
