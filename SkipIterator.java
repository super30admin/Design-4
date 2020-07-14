// Time Complexity : For advance: { O(n) --> where n is number of elements in the input list }; For Rest functions: O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : NA
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

class SkipIterator implements Iterator<Integer> {
	
	HashMap<Integer, Integer> map;
	Iterator<Integer> it;
	Integer nextEl;

	public SkipIterator(Iterator<Integer> it) {
		map = new HashMap<>();
		this.it = it;
		advance();
	}

	public boolean hasNext() {
		return nextEl != null;
	}

	public Integer next() {
		int temp = nextEl;
		advance();
		return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
		if (val == nextEl) advance();
		else map.put(val, map.getOrDefault(val, 0) + 1);
	}
	
	// used for putting nextEl pointer at the viable position (el) in my iterator 
	public void advance() {
		nextEl = null; // make previous nextEl as null
		while (nextEl == null && it.hasNext()) { // iterate till nextEl valid value is setup
			int el = it.next(); // el from original iterator
			if (map.containsKey(el)) { // if element is there in skip map already 
				map.put(el, map,get(el) - 1); // reduce its skip count by 1
				map.remove(el, 0); // remove the skip el if its count becomes zero
			}
			else {
				nextEl = el; // if the element doesn't need to be skipped, set it to nextEl of my skip iterator 
			}
		}
	}
}