// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

import java.util.*;

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Integer nextEl;
    Iterator it;

    public SkipIterator(Iterator<Integer> it) {

        // intialize iterator and hashmap
        this.it = it;
        this.map = new HashMap<>();
        advance();
    }

    public void advance() {
        // make nextEl null
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = (Integer) it.next();
            // check that my next element is in the map if it is in the map we skip that
            // element and remove it from the map else we make it as a nextEl
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
        // check this is last element or not
        return nextEl != null;
    }

    @Override
    public Integer next() {
        // store nextEl in result as we are updating nextEl in advance function
        Integer result = nextEl;
        // advance till next valid element
        advance();
        // return result
        return result;
    }

    public void skip(int num) {
        // if nextEl is not equal to null then we add this element in map or we advance
        // it to next valid element.
        if (nextEl != num) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        } else {
            advance();
        }
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(it.hasNext()); // true
        System.out.println(it.next()); // 2
        it.skip(5);
        System.out.println(it.next()); // 3
        System.out.println(it.next()); // 6
        it.skip(5);
        it.skip(7);
        System.out.println(it.next()); // 5
        System.out.println(it.next()); // -1
        System.out.println(it.next()); // 5
        System.out.println(it.next()); // 10

    }
}