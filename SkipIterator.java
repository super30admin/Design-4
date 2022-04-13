// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
class SkipIterator {
	private int index = 0;
	private int[] nums = null;
	private Map<Integer, Integer> map = null;
	public SkipIterator(int[] nums) {
		this.nums = nums;
		map = new HashMap<>();
	}

	/**
	* Returns true if the iteration has more elements.
	*/
	public boolean hasNext() {
		return index < nums.length;
	}

	/**
	* Returns the next element in the iteration.
	*/
	public Integer next() {
		Integer value = nums[index++];
		checkSkipped();
		return value;
	}
	
	private void checkSkipped() {
		while(index < nums.length && map.containsKey(nums[index])) {
			if(map.get(nums[index]) == 1) map.remove(nums[index]);
			else map.put(nums[index], map.get(nums[index])-1);
			++index;
		}
	}
}
