// Time complexity: O(1) for next() and skip() but O(1) amortized for hasNext()
// Space complexity: O(n)

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> it;
    Map<Integer, Integer> skipFrequencyMap;
    int nextVal;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.skipFrequencyMap = new HashMap<>();
    }

    public boolean hasNext() {
        while (it.hasNext()) {
            int el = it.next();
            if (skipFrequencyMap.containsKey(el)) {
                skipFrequencyMap.put(el, skipFrequencyMap.get(el) - 1);
                if (skipFrequencyMap.get(el) == 0) {
                    skipFrequencyMap.remove(el);
                }
                continue;
            }
            nextVal = el;
            return true;
        }

        return false;
    }

    public Integer next() {
        return nextVal;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public void skip(int val) {
        skipFrequencyMap.put(val, skipFrequencyMap.getOrDefault(val, 0) + 1);
    }

    public static void main(String[] args) {
        Integer[] testArr = new Integer[] { 2, 3, 5, 6, 5, 7, 5, -1, 5, 10 };
        SkipIterator itr = new SkipIterator(Arrays.asList(testArr).iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.hasNext());
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.hasNext());
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.hasNext());
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.hasNext());
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.hasNext());
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
    }
}