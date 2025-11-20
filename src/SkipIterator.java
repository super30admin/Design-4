import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 */

/**
 * @author Aditya Sinha
 * use a skipMap to track which numbers to skip and how many times
 * advance() method always sets nextEl to the next valid unskipped number
 * Time Complexity - next(), skip() : O(1) / hasNext(): O(1)
 * Space Complexity - O(k)
 *
 */
public class SkipIterator implements Iterator<Integer> {

	 HashMap<Integer, Integer> skipMap;
	    Integer nextEl;
	    Iterator<Integer> nit;

	    public SkipIterator(Iterator<Integer> it) {
	        this.nit = it;
	        this.skipMap = new HashMap<>();
	        advance();
	    }

	    public void advance() {
	        this.nextEl = null;
	        while (nit.hasNext()) {
	            Integer el = nit.next();
	            if (!skipMap.containsKey(el)) {
	                nextEl = el;
	                break;
	            } else {
	                skipMap.put(el, skipMap.get(el) - 1);
	                if (skipMap.get(el) == 0) {
	                    skipMap.remove(el);
	                }
	            }
	        }
	    }

	    public boolean hasNext() {
	        return nextEl != null;
	    }

	    public Integer next() {
	        Integer el = nextEl;
	        advance();
	        return el;
	    }

	    public void skip(int val) {
	        if (val == nextEl) {
	            advance();
	        } else {
	            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
	        }
	    }
}

 class SkipIteratorMain {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8).iterator());
        System.out.println(itr.hasNext()); // true
        itr.skip(5);
        System.out.println(itr.next()); // 6
        itr.skip(5);
        System.out.println(itr.next()); // 7
        System.out.println(itr.next()); // 6
        itr.skip(8);
        itr.skip(9);
        System.out.println(itr.next()); // 5
        System.out.println(itr.next()); // 5
        System.out.println(itr.next()); // 6
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // 8
        System.out.println(itr.hasNext()); // false
    }
}