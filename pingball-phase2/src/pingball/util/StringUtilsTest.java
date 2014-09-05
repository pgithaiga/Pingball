package pingball.util;

import static org.junit.Assert.assertEquals;
import static pingball.util.StringUtils.join;
import static pingball.util.StringUtils.joinNonEmpty;
import static pingball.util.StringUtils.repeat;

import org.junit.Test;

/*
 * Testing strategy:
 *
 * Test different forms of string inputs, including empty string,
 * one lengths strings, and multiple letter strings. For integer
 * inputs, test 0, 1, and larger values. When there are multiple
 * string parameters, use a cartesian product to generate test inputs.
 */
public class StringUtilsTest {

    @Test public void joinEmpty() {
        String res = join("");
        assertEquals("", res);
    }

    @Test public void joinEmptyNonEmptyDelimiter() {
        String res = join(" ");
        assertEquals("", res);
    }

    @Test public void joinSingleEmptyDelimiter() {
        String res = join("", "hello");
        assertEquals("hello", res);
    }

    @Test public void joinSingleSpaceDelimiter() {
        String res = join(" ", "hello");
        assertEquals("hello", res);
    }

    @Test public void joinMultipleSpaceDelimiter() {
        String res = join(" ", "hello", "world");
        assertEquals("hello world", res);
    }

    @Test public void joinMultipleNewlineDelimiter() {
        String res = join("\n", "hello", "world");
        assertEquals("hello\nworld", res);
    }

    @Test public void joinMultipleMultiCharDelimiter() {
        String res = join(", ", "hello", "world");
        assertEquals("hello, world", res);
    }

    @Test public void joinMultiple() {
        String res = join(", ", "one", "two", "three");
        assertEquals("one, two, three", res);
    }

    @Test public void joinNonEmptyEmpty() {
        String res = joinNonEmpty("");
        assertEquals("", res);
    }

    @Test public void joinNonEmptySingleEmpty() {
        String res = joinNonEmpty("", "");
        assertEquals("", res);
    }

    @Test public void joinNonEmptySingleSpaceDelimiter() {
        String res = joinNonEmpty(" ", "hello", "");
        assertEquals("hello", res);
    }

    @Test public void joinNonEmptyMultipleSpaceDelimiter() {
        String res = joinNonEmpty(" ", "hello", "", "world", "", "");
        assertEquals("hello world", res);
    }

    @Test public void joinNonEmptyMultipleNewlineDelimiter() {
        String res = joinNonEmpty("\n", "", "hello", "world");
        assertEquals("hello\nworld", res);
    }

    @Test public void joinNonEmptyMultipleMultiCharDelimiter() {
        String res = joinNonEmpty(", ", "hello", "", "", "world");
        assertEquals("hello, world", res);
    }

    @Test public void repeatEmpty() {
        String res = repeat("", 0);
        assertEquals("", res);
    }

    @Test public void repeatEmptyPositiveTimes() {
        String res = repeat("", 5);
        assertEquals("", res);
    }

    @Test public void repeatOnce() {
        String res = repeat("hello", 1);
        assertEquals("hello", res);
    }

    @Test public void repeatSingle() {
        String res = repeat("a", 3);
        assertEquals("aaa", res);
    }

    @Test public void repeatMultiple() {
        String res = repeat("banana", 4);
        assertEquals("bananabananabananabanana", res);
    }

}
