// Time Complexity: O(n) for next() and hasNext() and O(1) for skip. So overall O(n)
// Space Complexity: O(k) where k is the number of distinct values to be skipped.
import java.util.*;

class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> it;
    private Map<Integer, Integer> skipMap;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    private void advance() {
        nextEl = null;
        while (it.hasNext()) {
            Integer el = it.next();
            if (skipMap.containsKey(el)) {
                // Decrement the count for the skipped element
                skipMap.put(el, skipMap.get(el) - 1);
                if (skipMap.get(el) == 0) {
                    skipMap.remove(el);
                }
            } else {
                nextEl = el;
                break;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Integer result = nextEl;
        advance();
        return result;
    }

    //@Override
    public void skip(int val) {
        if (nextEl != null && nextEl.equals(val)) {
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 5, 7, 5);
        SkipIterator skipIt = new SkipIterator(nums.iterator());

        System.out.println(skipIt.next()); // 1
        skipIt.skip(5);
        System.out.println(skipIt.next()); // 2
        System.out.println(skipIt.next()); // 3
        skipIt.skip(5);
        System.out.println(skipIt.next()); // 4
        System.out.println(skipIt.next()); // 6 (skipped two 5s)
        System.out.println(skipIt.next()); // 7
        System.out.println(skipIt.hasNext()); // false
    }

}
