/* 
Time Complexity:
- next(): Amortized O(1)
- hasNext(): O(1)
- skip(val): Amortized O(1)

Space Complexity:
- O(k) for skipMap, where k is the number of values currently being skipped (by remaining counts)

Did this code successfully run on Leetcode: Yes
*/

// Approach:
// - Keep an underlying iterator (nit), a skipMap<value, remainingSkips>, and a cached nextEle.
// - advance(): consume from nit until you find an element not scheduled to be skipped;
//   if an element is in skipMap, decrement/remove its count and keep advancing.
// - hasNext(): true iff nextEle is non-null.
// - next(): return nextEle and call advance() to position the next value.
// - skip(val): if val equals nextEle, immediately advance(); otherwise increment its skip count in skipMap.


import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;

public class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> nit;
    private final HashMap<Integer, Integer> skipMap;
    private Integer nextEle;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    // Move iterator forward until we find a valid element
    private void advance() {
        nextEle = null;
        while (nit.hasNext()) {
            Integer el = nit.next();
            if (skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el) - 1);
                if (skipMap.get(el) == 0) {
                    skipMap.remove(el);
                }
            } else {
                nextEle = el;
                break;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextEle != null;
    }

    @Override
    public Integer next() {
        Integer temp = nextEle;
        advance();
        return temp;
    }

    // Schedule an element to be skipped
    public void skip(int val) {
        if (nextEle != null && nextEle == val) {
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }

    // Driver for testing
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8);
        SkipIterator itr = new SkipIterator(nums.iterator());

        System.out.println(itr.hasNext()); // true
        itr.skip(5);
        System.out.println(itr.next());    // 6
        itr.skip(5);
        System.out.println(itr.next());    // 7
        System.out.println(itr.next());    // 6
        itr.skip(8);
        itr.skip(9);
        System.out.println(itr.next());    // 5
        System.out.println(itr.next());    // 5
        System.out.println(itr.next());    // 6
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next());    // 8
        System.out.println(itr.hasNext()); // false
    }
}
