// Time Complexity:  next -> O(n), skip -> O(n), hasNext -> O(1)
// Space Complexity: O(m) where m is the number of unique elements in the hashmap when skip is called
public class Main {

    public static class SkipIterator implements Iterator<Integer> {
        Iterator<Integer> it;
        HashMap<Integer, Integer> skipMap;
        Integer nextEl;

        public SkipIterator(Iterator<Integer> it) {
            this.it = it;
            skipMap = new HashMap<>();
            advance();
        }

        public boolean hasNext() {
            return nextEl != null;
        }

        public Integer next() {
            Integer el = nextEl;
            advance();
            return el;
        }

        private void advance() {
            nextEl = null;
            while(nextEl == null && it.hasNext()) {
                nextEl = it.next();
                if(skipMap.containsKey(nextEl)) {
                    int cnt = skipMap.get(nextEl);
                    cnt--;
                    if (cnt == 0) {
                        skipMap.remove(nextEl);
                    } else {
                        skipMap.put(nextEl, cnt);
                    }
                    nextEl = null;
                }
            }
        }

        public void skip(int val) {
            if (nextEl == val) {
                advance();
            } else {
                skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
            }

        }

    }


    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2,3,5,6,5,7,5,-1,5,10).iterator());
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
        itr.skip(5);
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next());
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next());
    }

}

