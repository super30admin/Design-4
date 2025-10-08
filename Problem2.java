import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// Time Complexity : hasNext- O(1), next- O(n) since we could have all elements to be skipped, skip-O(n) since we could have all elements to be skipped after the current element is skipped
// Space Complexity : O(k) where k is the number of items in the skip map
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach

/**
 * Using an advance funtion to move to next valid location when next and skip functions are called so that the hasNext function doesn't return a bad true response when the last element on the iterator is a skipped number.
 */
public class Solution {

    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext());; // true
        System.out.println(itr.next());; // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // error
    }
}

class SkipIterator implements Iterator<Integer> {
    Map<Integer, Integer> skipMap;
    Iterator<Integer> nit;
    Integer nextEl;


    public SkipIterator(Iterator<Integer> it) {
        this.skipMap = new HashMap<>();
        this.nit = it;
        advance();
    }

    public boolean hasNext() {
        return nextEl != null;
    }

    public Integer next() {
        Integer temp = nextEl;
        // going to next valid location
        advance();
        return temp;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        // if the next element is the one to be skipped, don't add it to map and just advance to the next location
        if (val == nextEl) {
            advance();
        } else {
            // store it in the map for future skip
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }

    // Moving to next valid location
    private void advance() {
        nextEl = null;
        while(nit.hasNext() && nextEl == null) {
            Integer el = nit.next();
            // current element is the one to skip
            if (skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);
            } else {
                // current element is not the one to skip so next valid element will be the current
                nextEl = el;
                break;
            }
        }
    }
}
