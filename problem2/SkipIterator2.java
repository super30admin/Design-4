//Time Complexity : Skip: O(n), Next: O(n), HasNext: O(1), n -> Total number of elements in the list
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
package problem2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SkipIterator2 {
	Map<Integer, Integer> map;
	Iterator<Integer> numsItr;
	Integer nextEl;

	public SkipIterator2(Iterator<Integer> it) {
		this.numsItr = it;
		this.map = new HashMap<Integer, Integer>();
		advance();
	}

	public boolean hasNext() {
		return nextEl != null;
	}

	public Integer next() {
		Integer temp = nextEl;
		advance();
		return temp;
	}

	public void skip(int val) {
		if (val == nextEl) {
			advance();
		} else {
			map.put(val, map.getOrDefault(val, 0) + 1);
		}
	}

	private void advance() {
		nextEl = null;
		while (nextEl == null && numsItr.hasNext()) {
			Integer el = this.numsItr.next();
			if (!map.containsKey(el)) {
				nextEl = el;
			} else {
				map.put(el, map.get(el) - 1);
				if (map.get(el) == 0) {
					map.remove(el);
				}
			}
		}
	}

	public static void main(String[] args) {
		List<Integer> nums = Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10);
		SkipIterator2 itr = new SkipIterator2(nums.iterator());
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
