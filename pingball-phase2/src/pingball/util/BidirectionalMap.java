package pingball.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A bidirectional Map.
 *
 * Rep invariant:
 *
 * A mapping occurs in forwardMapping if and only if the reverse mapping
 * occurs in reverseMapping.
 */
public final class BidirectionalMap<K, V> {

    private final Map<K, V> forwardMapping = new HashMap<>();
    private final Map<V, K> reverseMapping = new HashMap<>();

    /**
     * Adds a forward mapping to this map.
     *
     * If necessary, this removes the previous mapping for the value.
     *
     * @param key The key to add to the map.
     *
     * @param value The value to add to the map.
     *
     * @return The value previously associated with the key.
     */
    public V putForward(K key, V value) {
        V oldValue = removeForward(key);
        removeReverse(value);
        forwardMapping.put(key, value);
        reverseMapping.put(value, key);
        checkRep();
        return oldValue;
    }

    /**
     * Adds a reverse mapping to this map.
     *
     * If necessary, this removes the previous mapping for the key.
     *
     * @param value The value to add to the map.
     *
     * @param key The key to add to the map.
     *
     * @return The key previously associated with the value.
     */
    public K putReverse(V value, K key) {
        K oldKey = removeReverse(value);
        removeForward(key);
        reverseMapping.put(value, key);
        forwardMapping.put(key, value);
        checkRep();
        return oldKey;
    }

    /**
     * Returns true if this map contains a mapping for a key.
     *
     * @param key The key to look up.
     *
     * @return Whether this map contains the key.
     */
    public boolean containsForward(K key) {
        checkRep();
        return forwardMapping.containsKey(key);
    }

    /**
     * Returns true if this map contains a mapping for a value.
     *
     * @param value The value to look up.
     *
     * @return Whether this map contains the value.
     */
    public boolean containsReverse(V value) {
        checkRep();
        return reverseMapping.containsKey(value);
    }

    /**
     * Gets a forward mapping from this map.
     *
     * @param key The key to look up.
     *
     * @return The value.
     */
    public V getForward(K key) {
        checkRep();
        return forwardMapping.get(key);
    }

    /**
     * Gets a reverse mapping from this map.
     *
     * @param value The value to look up.
     *
     * @return The key.
     */
    public K getReverse(V value) {
        checkRep();
        return reverseMapping.get(value);
    }

    /**
     * Removes a forward mapping from this map.
     *
     * @param key The key to remove.
     *
     * @return The value corresponding to the key.
     */
    public V removeForward(K key) {
        V oldValue = forwardMapping.remove(key);
        reverseMapping.remove(oldValue);
        checkRep();
        return oldValue;
    }

    /**
     * Removes a reverse mapping from this map.
     *
     * @param value The value to remove.
     *
     * @return The key corresponding to the value.
     */
    public K removeReverse(V value) {
        K oldKey = reverseMapping.remove(value);
        forwardMapping.remove(oldKey);
        checkRep();
        return oldKey;
    }

    /**
     * Returns the keyset of the map.
     *
     * @return The keyset.
     */
    public Set<K> keySet() {
        return forwardMapping.keySet();
    }

    /**
     * Returns the value set of the map.
     *
     * @return The value set.
     */
    public Set<V> valueSet() {
        return reverseMapping.keySet();
    }


    /**
     * Returns the size of this map.
     *
     * @return The size.
     */
    public int size() {
        checkRep();
        return forwardMapping.size();
    }

    private void checkRep() {
        // only run if assertions are enabled
        boolean assertionsEnabled = false;
        assert assertionsEnabled = true; // assignment
        if (assertionsEnabled) {
            assert forwardMapping.size() == reverseMapping.size() :
                "forward and reverse mappings don't have the same size";
            for (K elem: forwardMapping.keySet()) {
                assert elem.equals(reverseMapping.get(forwardMapping.get(elem)));
            }
            for (V val: reverseMapping.keySet()) {
                assert val.equals(forwardMapping.get(reverseMapping.get(val)));
            }
        }
    }

}
