// TC : O(n) if all the values in the list are given as skip, trying to access next element will have to traverse complete list,
// If no skip, next() function will return elements in O(1)
// SC: O(N) to store values thta needs to be skipped.

// 

import java.util.*;

public class skipIterator implements Iterator<Integer> {
	
	final Iterator<Integer> it;
	HashMap<Integer, Integer> map;
	Integer nextele;
	public skipIterator(Iterator<Integer> it) {
			map = new HashMap<>();
		 this.it = it;
		 advance();  // should be called if the hasNext() function is called at first.
	}
	
	public boolean hasNext() {
		return nextele != null;  // nextele will have next element value, is not it store null.
	}
	
	public Integer next() {
		
		if(!hasNext())
			throw new RuntimeException("invalid entry");
		Integer el = nextele;  // store the next element in variable
		advance(); // check if the value matches with skip, and update the nextele accordingly.
		return el;
	}
	
	public void skip(int val) {
		if(!hasNext())
			throw new RuntimeException("invalid entry");
		if(nextele == val) {
			advance();  // if the value matched with nexele, we need to advance to next value in list
		}else {
			map.put(val, map.getOrDefault(val, 0)+1); // we need to add into hashmap and keep a count so that we can check which is skipped values.
		}
		
	}
	
	public void advance() {
		nextele = null;
		while(nextele == null && it.hasNext()) {
			Integer el = it.next();  // store the next element
			if(!map.containsKey(el)) {  // if the element in not present in map(skip value) update the nextelement
				nextele = el;
			}else {
				map.put(el, map.get(el)-1); // if the value is present, reduce its count and do not update the next element since we dont want the nextelement to be skip value.
				// keep iterating over list until we find a value which is after skipped value.
				map.remove(el, 0);
			}
		}
	}

      public static void main(String[] args) {
    	  
    	skipIterator it = new skipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        System.out.println(it.next());
        it.skip(3);
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.next());

      }
}
