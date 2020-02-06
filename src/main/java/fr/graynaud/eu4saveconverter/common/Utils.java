package fr.graynaud.eu4saveconverter.common;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    public static Optional<LocalDate> nullSafeParseDate(String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(LocalDate.parse(s, Constants.DATE_FORMAT));
        } catch (DateTimeParseException ignored) {
            return Optional.empty();
        }
    }

    public static String generatePath(String s) {
        return Normalizer.normalize(s.toLowerCase().trim(), Normalizer.Form.NFD)
                         .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                         .replaceAll("[^\\p{Alnum}]+", "-");
    }
}
