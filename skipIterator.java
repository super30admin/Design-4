// Time Complexity : hashnext: O(1) - skip(), advance(), next() -O(n)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
// "static void main" must be defined in a public class.
class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    HashMap<Integer, Integer> map;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();
        advance();
    }

    private void advance() {
        this.nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (!map.containsKey(el)) {
                nextEl = el;
            } else {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            }
        }
    }

    public void skip(int num) {
        if (num == nextEl) {
            advance();
        } else {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        Integer result = nextEl;
        advance();
        return result;
    }
}



public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8).iterator());
        System.out.println(it.hasNext());
        it.skip(6);
        it.skip(6);
        it.skip(7);
        System.out.println(it.hasNext());
    }
}