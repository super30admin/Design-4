import java.util.Iterator;
import java.util.*;
//Time Complexity : O(N)
//Space Complexity : O(N)
//Did this code successfully run on Leetcode : Yes
//Any problem you faced while coding this : No

public class SkipIterator implements Iterator<Integer> {

	HashMap<Integer, Integer> skipMap;
	Iterator<Integer> it;
	Integer nextValue;

	public SkipIterator(Iterator<Integer> it) {

		skipMap = new HashMap<>();
		this.it = it;
		nextValue = null;
	}

	public boolean hasNext() {
		return nextValue != null;

	}

	public Integer next() {
		int temp = nextValue;
		advance();
		return temp;

	}

	/**
	 * 
	 * The input parameter is an int, indicating that the next element equals 'val'
	 * needs to be skipped.
	 * 
	 * This method can be called multiple times in a row. skip(5), skip(5) means
	 * that the next two 5s should be skipped.
	 * 
	 */

	public void skip(int val) {
		if (nextValue == val) {
			advance();
		} else {
			skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
		}
	}

	private void advance() {
		nextValue = null;
		while (nextValue == null && it.hasNext()) {
			Integer num = it.next();
			if (!skipMap.containsKey(num)) {
				nextValue = num;
			} else {
				skipMap.put(num, skipMap.getOrDefault(num, 0) - 1);
				skipMap.put(num, 0);
			}
		}
	}
}