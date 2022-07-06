//# Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

// Time Complexity: O(1)
// Space Complexity: O(n) 
// Did this code successfully run on Leetcode: Yes
// Any problem you faced while coding this: No

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class SkipIteratorClass implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Integer nextEl;
    Iterator<Integer> it;

    public SkipIteratorClass(Iterator<Integer> it) {
        this.it = it;
        map = new HashMap<>();
        advance();
    }

    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (map.containsKey(el)) {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            } else {
                nextEl = el;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        Integer result = nextEl;
        advance();
        return result;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public void skip(int num) {
        if (nextEl != num) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        } else {
            advance();
        }
    }
}

public class skipIterator {

    public static void main(String[] args) {

        SkipIteratorClass itr = new SkipIteratorClass(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        itr.hasNext(); // true
        System.out.println("Next: " + itr.next()); // returns 2
        itr.skip(5);
        System.out.println("Next: " + itr.next()); // returns 3
        System.out.println("Next: " + itr.next()); // returns 6 because 5 should be skipped
        System.out.println("Next: " + itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println("Next: " + itr.next()); // returns 7
        System.out.println("Next: " + itr.next()); // returns -1
        System.out.println("Next: " + itr.next()); // returns 10
        itr.hasNext(); // false
        System.out.println("Next: " + itr.next()); // error
    }
}
