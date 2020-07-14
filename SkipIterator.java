import java.util.Iterator;
import java.util.*;


public class SkipIterator implements Iterator<Integer> {
	Iterator<Integer> it;
	Map<Integer, Integer> map;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
		this.map = new HashMap<>();
		// currently while in constructor nextEle is null 
		// so set nextEle to correct next
        advance();
    }

    
    public boolean hasNext() {
        return nextEl != null;
    }

    
    public Integer next() {
        // if (!hasNext()) throw new RuntimeException("empty");
        Integer el = nextEl;
        advance();
        System.out.println("el : "+el);
        return el;
    }

    public void skip(int num) {
		// if (!hasNext()) throw new RuntimeException("empty");
		//  if current element is the skipped element
		//  we will simply advance, otherwise put into map
        if (nextEl == num) {
            advance();
        } else {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
    }

    private void advance() {
		nextEl = null;
		// this method is used for skipping the element if it is nextEle 
		//  this will keep on calling next until  it is not in skip map 
		
        while (nextEl == null && it.hasNext()) {
			// get next element
			Integer el = it.next();
			//  check  if this next element is in the skip map or not
			//  if not in map set it to the correct next element
			//  else reduce the count of element in map
			// if count becomes zero remove the element
            if (!map.containsKey(el)) {
                nextEl = el;
            } else {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            }
        }
	}
	public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        itr.hasNext(); // true
		itr.next(); // returns 2
		itr.skip(5);
		itr.next(); // returns 3
		itr.next(); // returns 6 because 5 should be skipped
		itr.next(); // returns 5
		itr.skip(5);
		itr.skip(5);
		itr.next(); // returns 7
		itr.next(); // returns -1
		itr.next(); // returns 10
		itr.hasNext(); // false
		itr.next(); // error
	}

 
}