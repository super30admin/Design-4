// Time Complexity : O(n), n is the length of numbers in the iterator
// Space Complexity : O(n), n is no. of elements to be skipped 
// Did this code successfully run on Leetcode : No
// Any problem you faced while coding this : No


import java.util.*;

class SkipIterator implements Iterator<Integer> {
	HashMap<Integer, Integer> map;	//to store the elements to be skipped and its count
	Iterator<Integer> it;	//native iterator
	Integer nextEl;	// to store the next element to be returned

	public SkipIterator(Iterator<Integer> it) {
		map = new HashMap<>();
		this.it = it;
		advance();	//to find the next element
	}

	public boolean hasNext() {
		return nextEl != null;	//if nextEl is not null, then value is present
	}

	public Integer next() {
		int temp = nextEl;	//store the value
		advance();	//find the next element
		return temp;	//return the stored value
	}

	/**
	 * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	 * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	 */ 
	public void skip(int val) {
		if(val == nextEl) 	 // if our value to be skipped is equal to the next value
			advance();	// call advance and move to next
		else
			map.put(val,  map.getOrDefault(val, 0) + 1);	 //else put the value to be skipped in map and increase the count if already there
	}

	private void advance() {
		nextEl = null;
		while(nextEl == null && it.hasNext()) {	// we will iterate the list using the iterator til there is a next element and we havent found a next el
			int el = it.next();	// get the element
			if(map.containsKey(el)) {	// check if map contains the element, skip it right here
				map.put(el, map.get(el) - 1);
				map.remove(el, 0);
			}
			else
				nextEl = el;	// if not we found the next element
		}
	}

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
	}
}


