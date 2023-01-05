// Google | Skip Iterator

// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : -


// Your code here along with comments explaining your approach

// https://leetcode.com/discuss/interview-question/341818

class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> nit;
    private final HashMap<Integer, Integer> skipMap;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    // Time Complexity : O(1)
    // Space Complexity : O(1)
    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    // Time Complexity : O(n)
    // Space Complexity : O(n)
    @Override
    public Integer next() {
        Integer re = nextEl;
        advance();
        return re;
    }

    // Time Complexity : O(n)
    // Space Complexity : O(n)
    public void skip(int num) {
        if (num == nextEl) {
            advance();
        } else {
            skipMap.put(num, skipMap.getOrDefault(num, 0) + 1);
        }
    }

    // Time Complexity : O(n)
    // Space Complexity : O(n)
    private void advance() {
        this.nextEl = null;
        while (nextEl == null && nit.hasNext()) {
            Integer el = nit.next();
            if (!skipMap.containsKey(el)) {
                nextEl = el;
            } else {
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);
            }
        }
    }
}

public class Main {
        public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }
}