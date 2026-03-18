// Time Complexity : O(1)
// Space Complexity : O(k), where k = number of skipped elements in the map
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Your code here along with comments explaining your approach
/*
The idea is to have an iterator of integer type and initialize it with the input iterator.We maintain a map
to track the skipped integers along with their frequency which would help us understand how amny times do we
need to skip a number if it appears mutiple times under skip.Now, have a global variable to track next Element
which helps us to solve next and hasNext methods. We need to have a helper method to iterate where we check
if the iterated element exists in the skip map, if so, decrement the frequency and remove it if the freq is 0.
If not, just update the next Element value and break. This way, we can keep nextElement field upto date as we
initialize it to null at the start of advance method which helps in case of skipped elemnent for the current
value.
 */

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nit;
    Integer nextEl;
    Map<Integer, Integer> skipMap;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    public boolean hasNext() {
        return nextEl != null;
    }

    public Integer next() {
        Integer temp = nextEl;
        advance();
        return temp;
    }

    private void advance() {
        nextEl = null;
        while(nit.hasNext()) {
            Integer el = nit.next();
            if(skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el) - 1);
                if(skipMap.get(el) == 0)
                    skipMap.remove(el);
            }
            else {
                nextEl = el;
                break;
            }
        }
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
    }
}

public class Main {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // error
    }
}