package s30;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class SkipIterator implements Iterator<Integer> {
	
	/* Approach: Initialize the iterator property when constructor is called.
	 * 1. Initialize a property "nextElem" that stores the next element to be returned.
	 * 2. When skip method is called on an element, it is stored in the map
	 *    along with the count that indicates number of times it should be skipped.
	 * 3. When hasNext method is called, return true if the nextElem property is not null else return false.
	 * 4. When next method is called, return the value present in "nextElem" property. 
	 *    Then, update the value of "nextElem" property to the next possible element that can be returned. 
	 *    Reset it to null if no element can be returned.
	 * 
	 * Time complexity: 
	 * hasNext: O(1)
	 * next: O(N) in the worst case, N is number of elements in the list, 
	 *       where every element in the list is to be skipped
	 * skip: O(1)
	 * 
	 * Space Complexity: size of hash map that stores skipped elements: 
	 *                   O(N), in the worst case where N is number of elements in list
	 * */
	
	HashMap<Integer, Integer> elemsToSkip = new HashMap<Integer, Integer>();
	Iterator<Integer> iter;
	Integer nextElem = null;
	
	public SkipIterator(Iterator<Integer> it) {
		iter = it;
	}

	public boolean hasNext() {
		return nextElem != null;
	}

	public Integer next() {
		Integer next = nextElem;
		updateNextElem();
		return next;
	}
	
	
	private void updateNextElem() {
		nextElem = null;
		while(iter.hasNext() && nextElem == null) {
			int curr = iter.next();
			if(elemsToSkip.containsKey(curr)) {
				int count = elemsToSkip.get(curr);
				elemsToSkip.put(curr, --count);
				if(count == 0) {
					elemsToSkip.remove(curr);
				}
			} else {
				nextElem = curr;
			}
		}
	}

	
	public void skip(int val) {
		elemsToSkip.put(val, elemsToSkip.getOrDefault(val, 0) + 1);
	}
	
	public static void main(String[] args) {
		Integer[] arr = new Integer[] {2, 3, 5, 6, 5, 7, 5, -1, 5, 10};
		SkipIterator itr = new SkipIterator(Arrays.asList(arr).iterator());
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
		System.out.println(itr.next()); // null
	}
}
