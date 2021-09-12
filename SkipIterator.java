/*
Time: O(n) where n = list.size()
Space: O(n) for HashMap
Did this code successfully run on Leetcode : NA
Any problem you faced while coding this : None
*/
public class SkipIterator {

    public static class SkipIterator implements Iterator<Integer> {
        private Iterator<Integer> it;
        HashMap<Integer, Integer> map;
        Integer nextEl;

        public SkipIterator(Iterator<Integer> it) {
            this.map = new HashMap<>();
            this.it = it;
            advance();
        }

        public boolean hasNext() {
            return nextEl != null;
        }

        public Integer next() {
            if (!it.hasNext())
                return -1;
            Integer temp = nextEl;
            advance();
            return temp;
        }

        public void advance() {
            nextEl = null;
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

        public void skip(int val) {
            if (val == nextEl) {
                advance();
            } else {
                map.put(val, map.getOrDefault(val, 0) + 1);
            }
        }
    }

    public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5, 6, 7, 7, 7, 8, 9, 10, 11).iterator());
        System.out.println(it.next());
        it.skip(7);
        it.skip(7);
        it.skip(7);
        System.out.println(it.next()); 
        System.out.println(it.hasNext()); 
        System.out.println(it.next()); 
        System.out.println(it.next()); 
    }

}