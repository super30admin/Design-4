
// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : 
// Any problem you faced while coding this : Yes, had to learn

/*
 * Have skipmap- precompute the next value when ever we add skip ; lets call it current
 * 
 */

import java.util.*;

public class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> elements;
    Integer nexEl;
    Map<Integer, Integer> skipMap;

    public SkipIterator(Iterator<Integer> it) {
        elements = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    public boolean hasNext() {
        return nexEl != null;
    }

    public Integer next() {
        Integer curr = nexEl; // this is the current val
        advance(); // we got current .. now advance to the future next
        return curr;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (val == nexEl) {
            advance(); // if the skip value is current.. advance to the next value.
        } else {
            this.skipMap.put(val, this.skipMap.getOrDefault(val, 0) + 1);
        }
    }

    public void advance() { // Helps get the nextEl the future nextElement
        this.nexEl = null;
        while (elements.hasNext()) {
            Integer el = elements.next();
            if (!skipMap.containsKey(el)) {
                this.nexEl = el;
                break;
            } else {
                this.skipMap.put(el, this.skipMap.get(el) - 1);
                if (this.skipMap.get(el) == 0)
                    this.skipMap.remove(el);
            }
        }
    }
}