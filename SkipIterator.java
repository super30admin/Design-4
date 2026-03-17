/**
 * Implements a Skip Iterator using a HashMap to track values to be skipped lazily.
 * The iterator maintains a pre-fetched next valid element and skips elements during advancement.
 * Skip requests are stored and applied only when those elements are encountered.
 * 
 * Time Complexity: O(1) average for next(), hasNext(), skip(); amortized over all elements
 * Space Complexity: O(n) in worst case for skipMap
 */

public class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> skipMap;
    Integer nextEl;
    Iterator<Integer> nit;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    public void advance() {
        this.nextEl = null;
        while (nit.hasNext()) {
            Integer el = nit.next();
            if (!skipMap.containsKey(el)) {
                nextEl = el;
                break;
            } else {
                skipMap.put(el, skipMap.get(el) - 1);
                if (skipMap.get(el) == 0) {
                    skipMap.remove(el);
                }
            }
        }
    }

    public boolean hasNext() {
        return nextEl != null;
    }

    public Integer next() {
        Integer el = nextEl;
        advance();
        return el;
    }

    public void skip(int val) {
        if (val == nextEl) {
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }
}
