// Time Complexity : next(): O(1) amortized, hasNext(): O(1), skip(): O(1)
// Space Complexity : O(n), where n = number of distinct elements being skipped (in HashMap)
// Did this code successfully run on Leetcode : Not a Leetcode problem, but it runs correctly for provided test cases
// Any problem you faced while coding this : No
// Approach -
//   - Wrap a normal iterator and maintain a `Map<Integer, Integer>` to track elements that should be skipped and how many times.
//   - On `skip(num)`, if `num` is the next element, skip it by advancing.
//     Otherwise, increment its count in the `count` map.
//   - On `next()` or `hasNext()`, use `advance()` to precompute the next valid element (not skipped).
//   - The `advance()` method skips over any elements currently marked to be skipped and updates counts accordingly.

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {

    private final Iterator<Integer> it;
    private final Map<Integer, Integer> count;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.count = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        if (!hasNext()) throw new RuntimeException("empty");
        Integer el = nextEl;
        advance();
        return el;
    }

    public void skip(int num) {
        if (!hasNext()) throw new RuntimeException("empty");
        if (nextEl == num) {
            advance();
        } else {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }
    }

    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (!count.containsKey(el)) {
                nextEl = el;
            } else {
                count.put(el, count.get(el) - 1);
                count.remove(el, 0);
            }
        }
    }
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }

}