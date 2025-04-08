// TC: O(1) hasNext, skip
// SC: O(k)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class SkipIterator implements Iterator<Integer> {

    private final Iterator<Integer> it;
    private final Map<Integer, Integer> count; // stores numbers to skip and how many times
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.count = new HashMap<>();
        advance(); // prepare the first valid nextEl
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        if (!hasNext()) throw new RuntimeException("No more elements.");
        Integer el = nextEl;
        advance(); // move to next valid element
        return el;
    }

    public void skip(int num) {
        if (!hasNext()) throw new RuntimeException("No more elements.");
        if (nextEl.equals(num)) {
            advance(); // skip immediately
        } else {
            count.put(num, count.getOrDefault(num, 0) + 1); // mark it to skip in future
        }
    }

    private void advance() {
        nextEl = null;
        while (it.hasNext()) {
            Integer el = it.next();
            if (!count.containsKey(el)) {
                nextEl = el;
                break;
            } else {
                count.put(el, count.get(el) - 1);
                if (count.get(el) == 0) {
                    count.remove(el); // cleanup skip record
                }
            }
        }
    }
}

// Test harness
public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext()); // true
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext()); // false
    }
}
// Output: true false