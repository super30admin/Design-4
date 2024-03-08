// Time Complexity : 
// Space Complexity : 
// Did this code successfully run on Leetcode : No
// Any problem you faced while coding this : No

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {
    Map<Integer, Integer> skipMap;
    Integer nextInt = null;
    Iterator<Integer> it;

    public SkipIterator(Iterator<Integer> it) {
        this.skipMap = new HashMap<>();
        this.it = it;
        advance();
    }

    // TC: O(1)
    // SC: O(1)
    private void advance() {
        this.nextInt = null;
        while (nextInt == null && this.it.hasNext()) {
            Integer el = it.next();
            if (!skipMap.containsKey(el)) {
                this.nextInt = el;
            } else {
                this.skipMap.put(el, this.skipMap.get(el) - 1);
                this.skipMap.remove(el, 0);
            }
        }
    }

    // TC: O(1)
    // SC: O(1)
    @Override
    public boolean hasNext() {
        return this.nextInt != null;
    }

    // TC: O(1)
    // SC: O(1)
    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new RuntimeException("empty");
        }
        Integer el = nextInt;
        advance();
        return el;
    }

    // TC: O(1)
    // SC: O(1)
    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (!hasNext()) {
            throw new RuntimeException("empty");
        }

        if (nextInt == val) {
            advance();
        } else {
            this.skipMap.put(val, this.skipMap.getOrDefault(val, 0) + 1);
        }
    }

    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());

        print(itr.hasNext()); // true
        print(itr.next()); // returns 2
        itr.skip(5);
        print(itr.next()); // returns 3
        print(itr.next()); // returns 6 because 5 should be skipped
        print(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        print(itr.next()); // returns 7
        print( itr.next()); // returns -1
        print(itr.next()); // returns 10
        print(itr.hasNext()); // false
        print(itr.next()); // error
    }

    private static void print(Object obj) {
        System.out.println(obj);
    }
}