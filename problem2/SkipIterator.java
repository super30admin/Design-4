//Time Complexity : Skip: O(n), Next: O(n), HasNext: O(1), n -> Total number of elements in the list
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
package problem2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {
	Map<Integer, Integer> map;
	int[] nums;
	int currIndex;

	public SkipIterator(int[] nums) {
		this.nums = nums;
		this.currIndex = 0;
		this.map = new HashMap<Integer, Integer>();
	}

	public boolean hasNext() {
		return this.currIndex < this.nums.length;
	}

	public Integer next() {
		Integer val = this.nums[this.currIndex++];
		checkSkipped();
		return val;
	}

	public void skip(int val) {
		this.map.put(val, this.map.getOrDefault(val, 0) + 1);
		checkSkipped();
	}

	private void checkSkipped() {
		while (this.currIndex < this.nums.length && map.containsKey(this.nums[this.currIndex])) {
			if (map.get(this.nums[this.currIndex]) == 1) {
				map.remove(this.nums[this.currIndex]);
			} else {
				map.put(this.nums[this.currIndex], map.get(this.nums[this.currIndex]) - 1);
			}
			this.currIndex++;
		}
	}

	public static void main(String[] args) {
		SkipIterator itr = new SkipIterator(new int[] { 2, 3, 5, 6, 5, 7, 5, -1, 5, 10 });
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
