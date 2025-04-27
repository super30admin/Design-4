import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nit; //native iterator
    HashMap<Integer, Integer> skipMap;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    private void advance() { //TC: O(n)
        nextEl = null;
        while (nit.hasNext()) {
            Integer el = nit.next();
            if (skipMap.containsKey(el)) {
                //decrement the count
                skipMap.put(el, skipMap.getOrDefault(el, 0) - 1);
                skipMap.remove(el, 0);
            } else {
                nextEl = el;
                break;
            }


        }
    }

    public boolean hasNext() { //O(1)
        return nextEl != null;
    }

    public Integer next() { //O(K) worst case ( K is number of skipped elements between current and next available).
        Integer temp = nextEl;
        advance();
        return temp;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) { //O(k) as it calls advance()
        if (nextEl == val) { //skip on current element
            advance();
        } else { //skip on future
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("Skip Iterator!");

        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println("Has next? " + itr.hasNext()); // true
        System.out.println("Next: " + itr.next());         // returns 2

        System.out.println("Skip 5");
        itr.skip(5);                                       // skips one 5

        System.out.println("Next: " + itr.next());         // returns 3
        System.out.println("Next: " + itr.next());         // returns 6 (5 is skipped)
        System.out.println("Next: " + itr.next());         // returns 5

        System.out.println("Skip 5");
        itr.skip(5);                                       // skips one 5
        System.out.println("Skip 5");
        itr.skip(5);                                       // skips another 5

        System.out.println("Next: " + itr.next());         // returns 7
        System.out.println("Next: " + itr.next());         // returns -1
        System.out.println("Next: " + itr.next());         // returns 10
        System.out.println("Has next? " + itr.hasNext());
    }
}


