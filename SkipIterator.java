//Time Complexity: O(n) where n is the length of numbers in the iterator
//Space COmplexity: O(n) where n is no. of elements to be skipped

//Integer nextel points to the right element 
//Iterator<Integer> iterator;  to iterate through the list
//Map<Integer, Integer> skipMap;  number and how many times a number has to be skipped

import java.util.*;

class SkipIterator implements Iterator<Integer> {

	HashMap<Integer, Integer> skipMap;
	Iterator<Integer> it;
	Integer nextel;

	public SkipIterator(Iterator<Integer> it) {
		skipMap = new HashMap<>();
		this.it = it;
		advance();
	}

	private void advance() {
		nextel = null;
		while (nextel == null && it.hasNext()) {
			Integer el = it.next();
			if (!skipMap.containsKey(el)) {
				nextel = el;
			} else {
				skipMap.put(el, skipMap.get(el) - 1);
				skipMap.remove(el, 0);
			}
		}
	}

	public boolean hasNext() {
		return nextel != null;
	}

	public Integer next() {
		int temp = nextel;
		advance();
		return temp;
	}



	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 

	public void skip(int val) {
		if (val == nextel) {
			advance();
		} else {
			skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
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