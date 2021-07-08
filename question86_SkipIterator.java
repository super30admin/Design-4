package Design4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/*
    Time Complexity: O(n)
    Space Complexity: O(n)
 */
public class question86_SkipIterator implements  Iterator<Integer> {
    /* Created by palak on 7/8/2021 */

        private Iterator<Integer> itr;
        Integer nextElement;
        HashMap<Integer, Integer> map;

        public question86_SkipIterator(Iterator<Integer> it) {
            this.map = new HashMap<>();
            this.itr = it;
            advance();
        }

        public boolean hasNext() {
            return nextElement != null;
        }

        public Integer next() {
            /** I will have to store the existing next element in a temp variable 'NextElement'.
             It is because the next will move the pointer to the next element and we will loose the access of
             the previous element.
             Then advance the pointer.
             */

            Integer temp = nextElement;
            advance(); // this will advance my pointer to new next element.
            return temp;
        }

        /**
         * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
         * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
         */
        public void skip(int val) {
            if(val == nextElement) {
                advance();
            } else {
                map.put(val, map.getOrDefault(val, 0) + 1);
            }
        }

        private void advance() {
            /** I am moving/ advancing my pointer to right using the Native Iterator. */
            nextElement = null;
            while(nextElement == null && itr.hasNext()) {
                Integer element = itr.next();
                if(!map.containsKey(element)) {
                    /** If that element is not present in Map, then it should not be skipped.
                     It is the next element. */
                    nextElement = element;
                } else {
                    /** Else has to be Skipped. */
                    map.put(element, map.get(element) - 1);
                    map.remove(element, 0);
                }
            }
        }

    public static void main(String[] args) {

        question86_SkipIterator skipIterator = new question86_SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6).iterator());

        System.out.println(skipIterator.hasNext());
        skipIterator.skip(5);
        System.out.println(skipIterator.next());
        skipIterator.skip(7);

        System.out.println(skipIterator.next());
        System.out.println(skipIterator.next());
        System.out.println(skipIterator.next());
        System.out.println(skipIterator.next());

        skipIterator.skip(1);
        skipIterator.skip(3);

        System.out.println(skipIterator.hasNext());
    }
}
