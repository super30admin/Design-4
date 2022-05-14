import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Problem2 {
    public static class skipIterator implements Iterator<Integer> {
        private Iterator<Integer> it;
        private HashMap<Integer, Integer> map;
        private Integer nextEl;

        public skipIterator(Iterator<Integer> it) {
            this.it = it;
            map = new HashMap<>();
            advance();
        }

        public boolean hasNext() {
            return nextEl != null;
        }

        public Integer next() {
            int answer = nextEl;
            advance();
            return answer;
        }

        public void skip(int num) {
            if (nextEl == num) {
                advance();
            } else {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
        }

        private void advance() {
            nextEl = null;
            while (nextEl == null && it.hasNext()) {
                int curr = it.next();
                if (map.containsKey(curr)) {
                    map.put(curr, map.get(curr) - 1);
                    map.remove(curr, 0);
                } else {
                    nextEl = curr;
                }
            }
        }
    }

    public static void main(String[] arg) {
        skipIterator st = new skipIterator(Arrays.asList(5, 6, 7, 7, 8, 9, 5, 6).iterator());
        System.out.println(st.next());
        System.out.println(st.hasNext());
        st.skip(7);
        System.out.println(st.next());
        System.out.println(st.hasNext());
        System.out.println(st.next());
        System.out.println(st.hasNext());
        st.skip(5);
        System.out.println(st.next());
        System.out.println(st.hasNext());
        System.out.println(st.next());
        System.out.println(st.hasNext());
    }
}
