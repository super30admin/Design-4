// Time Complexity : O(n),
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :    No
package design4;

import java.util.*;

class SkipIterator implements Iterator<Integer> {

    private Iterator<Integer> it;
    private Integer nextEl;
    private HashMap<Integer, Integer> map;

    public SkipIterator(Iterator<Integer> it) {
        this.map = map;
        this.it = it;
        advance();
    }

    public boolean hasNext() {
        return nextEl != null;
    }

    public Integer next() {
        Integer temp = nextEl;
        advance();
        return temp;
    }

    /**
     * The input parameter is an int, indicating that the next element equals
     * 'val' needs to be skipped. This method can be called multiple times in a
     * row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (val == nextEl) {
            advance();
        } else {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
    }

    private void advance() {
        nextEl = null;
        if (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (!map.containsKey(el)) {
                nextEl = el;
            } else {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            }
        }
    }
}
