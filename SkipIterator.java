import java.util.*;

class SkipIterator implements Iterator<Integer> {

	Integer cursor;	// points to the right element 
	Iterator<Integer> iterator; // to iterate through the list
	Map<Integer, Integer> skipCountMap; // number:how many times a number has to be skipped

	/*constructor */
	public SkipIterator(Iterator<Integer> it) {
		this.skipCountMap = new HashMap<>();
		this.iterator = it;
		seekCursor();
	}
	/*check if cursor is null, if so reached end of the list */
	public boolean hasNext() {
		return cursor != null;
	}
	/* return value pointed by cursor, now update cursor so the next call to next() method always returns valid value */
	public Integer next() {
		Integer curr = cursor;
		seekCursor();
		return curr;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
		if (cursor == val) {
			seekCursor(); 
		}
		else {
			// increment the skip count of that element whenever call to skip(element) is made
			skipCountMap.put(val, skipCountMap.getOrDefault(val,0)+1);
		}

	}
	/* helper method to always move cursor to right value and prevents return skipped elements */
	private void seekCursor() {
		cursor = null;

		while(iterator.hasNext() && cursor == null) {	// iterate till end of list or cursor value is not set
			Integer element = iterator.next();
			if(!skipCountMap.containsKey(element)) {	// not found in skip map, so cursor can point to element
				cursor = element;
			}
			else {
				skipCountMap.put(element, skipCountMap.get(element)-1);	// found skip element, ignore and reduce count
				skipCountMap.remove(element, 0);	// if skipcount is 0 now, remove it from map
			}
		}
	}

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10);
		Iterator it = list.iterator();
		SkipIterator itr = new SkipIterator(it);
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