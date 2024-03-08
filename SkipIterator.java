// https://leetcode.com/discuss/interview-question/341818

// TC
// hasNext() - O(1)
// next() - O(N) => Amortized O(1)
// skip() - O(N) => Amortized O(1)
// advance() - O(N) => Amortized O(1)

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {
    private Map<Integer, Integer> skipNumberToFreqMap;
    private Iterator<Integer> nativeIterator;
    private Integer nextElement;

    public SkipIterator(Iterator<Integer> it) {
        this.skipNumberToFreqMap = new HashMap<>();
        this.nativeIterator = it;
        advance(); // nextElement = arr[0] and next -> arr[1]
    }

    @Override
    public boolean hasNext() {
        return nextElement != null;
    }

    @Override
    public Integer next() {
        Integer next = nextElement;
        advance();
        return next;
    }

    public void skip(int num) {
        if (nextElement == num) { // nextElement=5 and skip(5)
            advance();
        } else {
            skipNumberToFreqMap.put(num, skipNumberToFreqMap.getOrDefault(num, 0) + 1);
        }
    }

    private void advance() {
        nextElement = null; // If all elements are skipped
        while (nativeIterator.hasNext()) {
            Integer currElement = nativeIterator.next();
            if (skipNumberToFreqMap.containsKey(currElement)) {
                skipNumberToFreqMap.put(currElement, skipNumberToFreqMap.get(currElement) - 1);
                skipNumberToFreqMap.remove(currElement, 0);
            } else {
                nextElement = currElement;
                break; // If there is a nextElement
            }
        }
    }

    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
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
        System.out.println(itr.next()); // null
    }
}