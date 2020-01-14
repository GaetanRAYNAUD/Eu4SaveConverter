package fr.graynaud.eu4saveconverter.common;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

public final class Utils {

    private Utils() {
    }

    public static Optional<Long> nullSafeParseLong(String s) {
        if (s == null) {
            return Optional.empty();
        }

        return Optional.of(Long.valueOf(s));
    }

    public static Optional<Double> nullSafeParseDouble(String s) {
        if (s == null) {
            return Optional.empty();
        }

        return Optional.of(Double.valueOf(s));
    }

    public static Optional<Date> nullSafeParseDate(String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Constants.DATE_FORMAT.parse(s));
        } catch (ParseException ignored) {
            return Optional.empty();
        }
    }
}
