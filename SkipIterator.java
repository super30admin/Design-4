// Time Complexity : O(N)
// Space Complexity : O(N)

// "static void main" must be defined in a public class.
public class Main {
    public static class SkipIterator implements Iterator<Integer> {
        Iterator<Integer> it;
        Integer nextEl;
        HashMap<Integer, Integer> map;

        public SkipIterator(Iterator<Integer> it) {
            this.it = it;
            map = new HashMap();
            advance();
        }

        public Integer next() {
            Integer temp = nextEl;
            advance();
            return temp;
        }

        public boolean hasNext() {
            return nextEl != null;
        }

        public void skip(int val) {
            if (val == nextEl) {
                advance();
            } else {
                map.put(val, map.getOrDefault(val, 0) + 1);
            }
        }

        public void advance() {
            nextEl = null;
            while (nextEl == null && it.hasNext()) {
                int curr = it.next();
                if (map.containsKey(curr)) {
                    map.put(curr, map.getOrDefault(curr, 0) - 1);
                    map.remove(curr, 0);
                } else {
                    nextEl = curr;
                }
            }
        }
    }

    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(2, 2, 3, 5, 5, 6, 7, 8).iterator());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        it.skip(3);
        System.out.println(it.hasNext());
        System.out.println(it.next());
        it.skip(5);
        it.skip(5);
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
    }
}
