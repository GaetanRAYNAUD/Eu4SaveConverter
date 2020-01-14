package fr.graynaud.eu4saveconverter.common;

import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ParseUtils {

    private ParseUtils() {
    }

    public static Optional<String> parseString(String content, String key) {
        if (content.contains(key)) {
            return Optional.of(getCleanString(content, key));
        }

        return Optional.empty();
    }

    public static Optional<Long> parseLong(String content, String key) {
        if (content.contains(key)) {
            return Utils.nullSafeParseLong(getCleanString(content, key));
        }

        return Optional.empty();
    }

    public static Optional<Double> parseDouble(String content, String key) {
        if (content.contains(key)) {
            return Utils.nullSafeParseDouble(getCleanString(content, key));
        }

        return Optional.empty();
    }

    public static Optional<Boolean> parseBoolean(String content, String key) {
        if (content.contains(key)) {
            return Optional.of(getCleanString(content, key).equals("yes"));
        }

        return Optional.empty();
    }

    public static List<String> parseListString(String content, String key) {
        return getCleanListString(content, key);
    }

    public static List<Long> parseLineLong(String content, String key) {
        if (content.contains(key)) {
            return getCleanLineString(content, key).stream()
                                                   .map(Utils::nullSafeParseLong)
                                                   .filter(Optional::isPresent)
                                                   .map(Optional::get)
                                                   .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public static List<String> parseLineString(String content, String key) {
        if (content.contains(key)) {
            return getCleanLineString(content, key);
        }

        return new ArrayList<>();
    }

    public static List<Boolean> parseLineLongToBoolean(String content, String key) {
        if (content.contains(key)) {
            return parseLineLong(content, key).stream()
                                              .map(l -> l == 1L)
                                              .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public static Optional<Date> parseDate(String content, String key) {
        try {
            return Utils.nullSafeParseDate(getCleanString(content, key));
        } catch (DateTimeParseException ignored) {
            return Optional.empty();
        }
    }

    public static boolean hasQuotes(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        return s.startsWith("\"") && s.endsWith("\"");
    }

    public static int getEndOfObject(String s, int beginIndex) {
        int opening = 0;
        int closing = 0;
        int index = 0;
        char c;

        for (int i = beginIndex; i < s.length(); i++) {
            c = s.charAt(i);

            if (c == '{') {
                opening++;
            } else if (c == '}') {
                closing++;
            }

            if (closing > opening) {
                return index;
            }

            index++;
        }

        return index;
    }

    public static int skipNextObject(String s, int beginIndex) {
        int opening = 0;
        int closing = 0;
        int index = 0;
        char c;

        for (int i = beginIndex; i < s.length(); i++) {
            c = s.charAt(i);
            index++;

            if (c == '{') {
                opening++;
            } else if (c == '}') {
                closing++;
            }

            if (opening != 0 && closing == opening) {
                return index;
            }
        }

        return index;
    }

    public static List<String> getListSameString(String content, String key) {
        List<String> value = null;

        if (content.contains(key + "=")) {
            value = Arrays.stream(content.trim().split(key))
                          .skip(1)
                          .map(ParseUtils::formatStringValue)
                          .filter(Objects::nonNull)
                          .map(s -> {
                              int index = s.indexOf("\n");
                              if (index >= 0) {
                                  s = s.substring(0, index);
                              }

                              return s.replace("=", "");
                          })
                          .map(ParseUtils::formatStringValue)
                          .filter(Objects::nonNull)
                          .collect(Collectors.toList());
        }

        return value;
    }

    public static List<String> getCleanListString(String content, String key) {
        int keyIndex = content.indexOf(key + "={");
        List<String> value = new ArrayList<>();

        if (keyIndex >= 0) {
            keyIndex += key.length() + 2;
            int endIndex = content.indexOf("}", keyIndex);

            if (endIndex < 0) {
                endIndex = content.length();
            }

            value = Arrays.stream(content.substring(keyIndex, endIndex).trim().split("[\r|\n]+"))
                          .map(ParseUtils::formatStringValue)
                          .filter(Objects::nonNull)
                          .collect(Collectors.toList());
        }

        return value;
    }

    public static List<String> getListObject(String content, String key) {
        int keyIndex = content.indexOf(key + "={");
        List<String> value = new ArrayList<>();

        if (keyIndex >= 0) {
            keyIndex += key.length() + 2;
            int endIndex = keyIndex + getEndOfObject(content, keyIndex);

            if (endIndex < 0) {
                endIndex = content.length();
            }

            String copy = content.substring(keyIndex, endIndex).trim();
            value = new ArrayList<>();
            int index = 0;

            while (index < copy.length()) {
                String nextObject = getNextObject(copy, index);
                value.add(nextObject.trim());
                index += nextObject.length();
            }
        }

        return value;
    }

    public static List<String> getListSameObject(String content, String key) {
        List<String> value = new ArrayList<>();

        if (content.contains(key)) {
            value = Arrays.stream(content.split(Pattern.quote(key)))
                          .skip(1)
                          .map(s -> key + s)
                          .map(s -> getNextObject(s, 0))
                          .collect(Collectors.toList());
        }

        return value;
    }

    public static String getNextObject(String content, int beginIndex) {
        return content.substring(beginIndex, beginIndex + skipNextObject(content, beginIndex));
    }

    public static String formatStringValue(String s) {
        s = s.trim();

        if (s.isEmpty() || s.isBlank()) {
            return null;
        }

        if (hasQuotes(s)) {
            return s.substring(1, s.length() - 1);
        } else {
            return s;
        }
    }

    public static List<String> getCleanLineString(String content, String key) {
        int keyIndex = content.indexOf(key + "={");
        List<String> value = null;

        if (keyIndex >= 0) {
            keyIndex += key.length() + 2;
            int endIndex = content.indexOf("}", keyIndex);

            if (endIndex < 0) {
                endIndex = content.length();
            }

            value = Arrays.stream(content.substring(keyIndex, endIndex).trim().split("\\s+"))
                          .map(ParseUtils::formatStringValue)
                          .filter(Objects::nonNull)
                          .collect(Collectors.toList());

        }

        return value;
    }

    public static String getCleanString(String content, String key) {
        int keyIndex = content.indexOf(key + "=");
        String value = null;

        if (keyIndex >= 0) {
            keyIndex += key.length() + 1;
            int endIndex = content.indexOf("\n", keyIndex);

            if (endIndex < 0) {
                endIndex = content.length();
            }

            value = formatStringValue(content.substring(keyIndex, endIndex));
        }

        return value;
    }
}
