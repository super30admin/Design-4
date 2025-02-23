
/**
 * Time Complexity: O(1)
 * Space Complexity: O(n) where n is number of skipped elements.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> it;
    Map<Integer, Integer> skippedElements;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.skippedElements = new HashMap<>();
    }

    public boolean hasNext() {
        return it.hasNext();
    }

    public Integer next() {
        if (!it.hasNext()) {
            return -1;
        }
        int nextEl = it.next();
        if (skippedElements.containsKey(nextEl)) {
            skippedElements.put(nextEl, skippedElements.get(nextEl) - 1);
            if (skippedElements.get(nextEl) == 0) {
                skippedElements.remove(nextEl);
            }
            if (!it.hasNext()) {
                return -1;
            }
            return it.next();
        }
        return nextEl;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public void skip(int val) {
        skippedElements.put(val, skippedElements.getOrDefault(val, 0) + 1);
    }
}