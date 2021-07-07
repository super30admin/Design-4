// Time Complexity : O(1) 
// Space Complexity : O(n) n -> no. of elements to be skipped
// Did this code successfully run on Leetcode : Yes // Ran on eclipse
// Any problem you faced while coding this : No

// Approach : 
/*
 * The skip iterator should work in a similar fashion as a normal iterator. The only difference is that we need to keep track of the values to be skipped.
 * To keep track of the skip numbers, map data structure is used to map value and corresponding count.
 * A seekCursor function is used for placing the cursor at the right position everytime.
 */

import java.util.*;

class SkipIterator implements Iterator<Integer> {
	
	private Iterator iterator;
	private Map<Integer, Integer> skipMap;
	private Integer cursor;

	public SkipIterator(Iterator iterator) {
		this.iterator = iterator;
		skipMap = new HashMap<>();
		seekCursor();
	}

	public boolean hasNext() {
		return cursor != null;
	}

	public Integer next() {
		Integer element = cursor;
		seekCursor();
		return element;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
		skipMap.put(val, skipMap.getOrDefault(val, 0)+1);
	}
	
	private void seekCursor() {
		cursor = null;
		while(cursor == null && iterator.hasNext()) {
			Integer curr = (Integer) iterator.next();
			if(!skipMap.containsKey(curr))
				cursor = curr;
			else {
				skipMap.put(curr, skipMap.get(curr)-1);
				skipMap.remove(curr,0);
			}
		}
		
	}
	
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10);
        Iterator iterator = list.iterator();
        SkipIterator itr = new SkipIterator(iterator);
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

