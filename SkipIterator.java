import java.util.Iterator;
//import java.util.Arrays;

//Time complexity: O(n)
//Space Complexity: O(n)

public class Main {
    public static class SkipIterator implements Iterator<Integer> {
        Iterator<Integer> it;
        HashMap<Integer, Integer> map;
        Integer nextEl;

        public SkipIterator(Iterator<Integer> it) {
            this.it = it;
            map = new HashMap<>();
            advance();
        }

        public Integer next() {
            int result = nextEl;
            advance();
            return result;
        }

        public boolean hasNext() {
            return nextEl != null;
        }

        private void advance() {
            nextEl = null;
            while (nextEl == null && it.hasNext()) {
                if (map.containsKey(nextEl)) {
                    map.put(nextEl, map.get(nextEl) - 1);
                    map.remove(nextEl, 0);
                } else {
                    nextEl = it.next();
                    break;
                }
            }
        }

        private void skip(int val) {
            if (nextEl == val) {
                advance();
            } else {
                map.put(val, map.getOrDefault(val, 0) + 1);
            }
        }
    }

    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(1, 1, 2, 2, 3, 3, 4, 5, 6).iterator());
        itr.skip(2);
        itr.skip(2);
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        itr.skip(5);
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
        System.out.println(itr.hasNext());

    }
}