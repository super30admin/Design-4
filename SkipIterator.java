// Time Complexity : advance(), skip(), next() : O(N), hasNext() : O(1)
// Space Complexity : O(N), hashmap
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
public class SkipIterator {
    class SkipIterator implements Iterator<Integer> {
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
}
