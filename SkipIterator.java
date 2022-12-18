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

	/**
	* The input parameter is an int, indicating that the next element equals 'num' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int num) {
		map.put(num, map.getOrDefault(num, 0)+1);
		checkSkipped();
	}
}