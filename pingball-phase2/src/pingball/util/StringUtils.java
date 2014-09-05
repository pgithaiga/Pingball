package pingball.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StringUtils {

    /**
     * Join strings using a delimiter.
     *
     * @param delimiter The delimiter to use to join the strings.
     *
     * @param elements The strings to join together.
     *
     * @return A string consisting of the elements joined by the delimiter.
     */
    public static String join(String delimiter, List<String> elements) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i));
            if (i < elements.size() - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    /**
     * Join strings using a delimiter.
     *
     * @param delimiter The delimiter to use to join the strings.
     *
     * @param elements The strings to join together.
     *
     * @return A string consisting of the elements joined by the delimiter.
     */
    public static String join(String delimiter, String... elements) {
        return join(delimiter, Arrays.asList(elements));
    }

    /**
     * Join nonempty strings using a delimiter.
     *
     * @param delimiter The delimiter to use to join the strings.
     *
     * @param elements The strings to join together.
     *
     * @return A string consisting of the elements joined by the delimiter.
     */
    public static String joinNonEmpty(String delimiter, List<String> elements) {
        List<String> filtered = new ArrayList<String>();
        for (String s: elements) {
            if (!s.isEmpty()) {
                filtered.add(s);
            }
        }
        return join(delimiter, filtered);
    }

    /**
     * Join nonempty strings using a delimiter.
     *
     * @param delimiter The delimiter to use to join the strings.
     *
     * @param elements The strings to join together.
     *
     * @return A string consisting of the elements joined by the delimiter.
     */
    public static String joinNonEmpty(String delimiter, String... elements) {
        return joinNonEmpty(delimiter, Arrays.asList(elements));
    }

    /**
     * Repeat a string a certain number of times.
     *
     * @param what The string to repeat.
     *
     * @param times The number of times to repeat the string
     * (must be nonnegative).
     *
     * @return The string repeated the number of times.
     */
    public static String repeat(String what, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(what);
        }
        return sb.toString();
    }

}
