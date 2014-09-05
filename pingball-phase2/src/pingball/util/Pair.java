package pingball.util;

/**
 * An immutable Pair.
 * 
 * Pair provides the functionality of an immutable pair of objects.
 * This class does not guarantee immutability when the objects contained
 * within this pair are mutable themselves.
 * 
 * Rep-Invariant:
 *      None. There is no specific limitation on the value of each object in the pair
 */
public class Pair<T, U> {

    private final T first;
    private final U second;

    /**
     * Make a pair.
     *
     * @param first The first element of the pair.
     *
     * @param second The second element of the pair.
     */
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Make a pair.
     *
     * @param first The first element of the pair.
     *
     * @param second The second element of the pair.
     */
    public static <T, U> Pair<T, U> of(T first, U second) {
        return new Pair<>(first, second);
    }

    /**
     * Returns the first element of the pair.
     *
     * @return The first element of the pair.
     */
    public T getFirst() {
        return first;
    }

    /**
     * Returns the second element of the pair.
     *
     * @return The second element of the pair.
     */
    public U getSecond() {
        return second;
    }

    /**
     * Returns true if this is equal to another pair.
     *
     * Two pairs are equal if and only if their first elements are equal
     * and their second elements are equal.
     *
     * @param that The pair to compare this to.
     *
     * @return Whether this pair is equal to another pair.
     */
    @Override public boolean equals(Object that) {
        if (that instanceof Pair<?, ?>) {
            Pair<?, ?> thatPair = (Pair<?, ?>) that;
            return this.first.equals(thatPair.first) &&
                this.second.equals(thatPair.second);
        }
        return false;
    }

    /**
     * Returns the hash code of this pair.
     *
     * @return The hash code of this pair.
     */
    @Override public int hashCode() {
        int firstHash = first.hashCode();
        int secondHash = second.hashCode();
        return firstHash ^ ((secondHash >>> 16) | secondHash << 16);
    }

    /**
     * Returns a string representation of this pair.
     *
     * Generates a string representation of this pair using the string
     * representations of the first and second elements.
     *
     * @return The string representation of this pair.
     */
    @Override public String toString() {
        return String.format("(%s,%s)", first.toString(), second.toString());
    }

}
