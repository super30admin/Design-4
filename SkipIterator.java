import java.util.Iterator;
import java.util.HashMap;
import java.util.Arrays;
public class SkipIterator implements Iterator<Integer> {

        HashMap<Integer, Integer> skipMap; // map of skip elements

        Integer nextEl; // next pointer of skip iterator
        Iterator<Integer> nit; // native iterator

        public SkipIterator(Iterator<Integer> it) {

            this.skipMap = new HashMap<>();

            this.nit = it;

            // start advancing
            advance();

        }

        public void advance() { //O(N)

            this.nextEl = null;

            // while nit = native iterator has next (non-null) pointer ,and next pointer (nextEl) of it (skip iterator) is null
            while(nit.hasNext() && nextEl == null) {

                // store next integer of native iterator in temp
                Integer temp = nit.next();

                // if temp is in skip map as a key
                if(skipMap.containsKey(temp)) {

                    // reduce value count of temp key by 1 as we are skipping it and not processing in output
                    skipMap.put(temp, skipMap.get(temp) - 1);

                    // when value of temp key is zero, remove it from skip map to avoid redundancy
                    skipMap.remove(temp, 0);
                }
                else {

                    //if temp is not in skip map as a key, set next pointer of skip iterator as temp
                    nextEl = temp;
                }

            }

        }

       //  @override
        public boolean hasNext() { // O(1)

            // is next pointer of skip iterator is set, true
            return nextEl != null;
        }

        // @override
        public Integer next() { // O(N)

            // next pointer of skip iterator readily gives a value without lag, store it before making advance
            Integer result = nextEl;

            // advance to reset pointers
            advance();

            // returned stored value of next pointer of skip iterator
            return result;
        }


        public void skip(int val) { // O(N)

            // if value to be skipped is current next pointer of skip iterator, don't go for skip map but directly skip
            if(val == nextEl) {

                // (skip and) advance
                advance();
            }
            else {

                // if value to be skipped is future next pointer of skip iterator, add it to skip map with default value zero
                skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
            }
        }


        public static void main(String[] args) {
            SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
            System.out.println(it.hasNext());
            it.skip(2);
            it.skip(1);
            it.skip(3);
            System.out.println(it.hasNext());
        }


}

/*

TIME COMPLEXITY = O(N) - worst case

skip, advance, next methods - O(N)

hasNext method - O(1)
 */


/*
 * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
 * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
 */