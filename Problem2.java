// Time Complexity : O(1)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

public class Problem2 {
    class SkipIterator implements Iterator<Integer> {

        HashMap<Integer, Integer> skipMap;
        Integer nextEl;
        Iterator<Integer> nit;
        public SkipIterator(Iterator<Integer> it) {

            this.skipMap = new HashMap<>();
            this.nit = it;
            advance();
        }
        private void advance(){
            nextEl = null;
            while (nextEl == null && nit.hasNext()) {
                Integer el = nit.next();
                if (skipMap.containsKey(el)) {
                    skipMap.put(el, skipMap.get(el) - 1);
                    skipMap.remove(el, 0);
                }
                else{
                    this.nextEl = el;
                }
            }
        }

        }

        @Overide
        public boolean hasNext() {
            return nextEl !=null;
        }

       @Overide
        public Integer next() {
            Integer re = nextEl;
            advance();
            return re;
        }

        /**
         * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
         * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
         */
        public void skip(int val) {
            if(val == nextEl){
                advance();
            }
            else{
                skipMap.put(val, skipMap.getOrDefault(num,0) +1);
            }
        }
    }

