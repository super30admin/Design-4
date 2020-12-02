
// Time Complexity : O(N)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Iterator<Integer> it;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        map = new HashMap<>();
        this.it = it;
        advance();
        // nextEl = it.next();
    }

    public boolean hasNext() {
        return nextEl != null;
    }

    public Integer next() {

        Integer temp = nextEl;
        advance();
        return temp;

    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped. This method can be called multiple times in a row.
     * skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (val == nextEl) {
            advance();
        } else {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
    }

    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (map.containsKey(el)) {
                map.put(el, map.getOrDefault(el, 0) - 1);
                map.remove(el, 0);
            } else {
                nextEl = el;
            }
        }
    }
}