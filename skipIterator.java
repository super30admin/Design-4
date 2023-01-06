// "static void main" must be defined in a public class.
// Time Complexity : 
/*
advance: O(n)
hasNext:O(1)
next:O(1)
skip:O(1)
*/
// Space Complexity :  
/*
advance: O(n)
hasNext:O(1)
next:O(1)
skip:O(1)
*/
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this :
/* Got more clarification during class for implementing it for O(1) complexity */

// Your code here along with comments explaining your approach
/* 
 * Maintain 2 maps to stores followed list list and tweets per userId
 */

class SkipIterator implements Iterator<Integer> {
    private HashMap<Integer, Integer> skipMap;
    Iterator<Integer> nit;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    private void advance() {
        this.nextEl = null;
        while (nextEl == null && nit.hasNext()) {
            int el = nit.next();
            if (skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);
            } else {
                nextEl = el;
            }
        }
    }

    public boolean hasNext() {

        return nextEl != null;
    }

    public Integer next() {
        Integer re = nextEl;
        advance();
        return re;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (val == nextEl) {
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).iterator());
        System.out.println(it.hasNext());
        it.next();
        it.skip(4);
        it.skip(1);
        it.skip(6);
        System.out.println(it.hasNext());
    }
}