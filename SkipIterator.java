/*Problem:
 * 
 * Design a SkipIterator that supports a method skip(int val). 
 * 
 * When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.
 * 
 */


/*
 * Time Complexity: O (N) -> To traverse through 'N' elements of an iterator
 * 
 * Space Complexity: O (M) -> 'M' elements to be skipped, their value and count of calls to skip() method is stored in HashMap
 * 
 * Did this code successfully run on leetcode: Yes
 * 
 * Any problem you faced while coding this: Yes, took time to understand the problem
 * 
 */

package com.s30.edu.design4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class SkipIterator implements Iterator<Integer>{
	// Declare the Iterator, HashMap and nextElement
	// Iterator is input, HashMap to store the value to skip and its count(how many times to skip that number when it is encountered)
	// nextElement to keep track of the number we are at currently in iterator
	Iterator<Integer> it;
	HashMap<Integer, Integer> count;
	Integer nextElement;
	
	// Initialize the iterator and HashMap in constructor
	public SkipIterator(Iterator<Integer> it) {
		this.it = it;
		count = new HashMap<>();
		// Call the findNext() method initially when constructor is invoked
		findNext();
	}

	@Override
	public boolean hasNext() {
		// Return if Iterator has next element, true or false
		return nextElement != null;
		
	}

	@Override
	public Integer next() {
		// When Iterator reaches the last element, call to hasNext() will throw a runtime exception
		if (!hasNext()) {
			throw new RuntimeException("empty");
		}
		// We store the nextElement value to temp variable, then call findNext() method where the next element is stored in "nextElement" or it is skipped if element has its entry in HashMap
		// Once we have found our next element, we break from findNext() method and return the nextElement value of previous call to next() method
		int temp = nextElement;
		findNext();
		return temp;
	}
	
	public void skip(int val) {
		// If the value of "nextElement" is to be skipped then call findNext() method, where it will skipped and its next element will be returned in "nextElement" variable
		if(val == nextElement) {
			findNext();
		}
		// Else, if we have still not encountered the element to be skipped, mark its entry in HashMap to skip that element when it is encountered
		else {
			count.put(val, count.getOrDefault(val, 0) + 1);
		}
	}
	
	// This method finds the next element in iterator
	// It also skips the element that has entry in HashMap
	public void findNext() {
		nextElement = null;
		
		// Here, we are calling next() and hasNext() methods of iterator itself and not our custom methods
		while(nextElement == null && it.hasNext()) {
			int el = it.next();
			
			// If an element has a entry in HashMap, it is skipped and its count is reduced in a HashMap to indicate that call to skip that element is executed
			if(count.containsKey(el)) {
				count.put(el, count.get(el) - 1);
				// If count of an element in a map is zero, then remove it from HashMap
				count.remove(el, 0);
			}
			// Else, we assign the next element to "nextElement" variable and break from the loop
			else {
				nextElement = el;
				break;
			}
			
		}
	}
	
	// Main method
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

		itr.hasNext(); // false

		System.out.println(itr.next()); // error
		
	}

}
