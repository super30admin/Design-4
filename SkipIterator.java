class SkipIterator {
	private int index = 0;
	private int[] nums = null;
	private Map<Integer, Integer> map = null;
	public SkipIterator(int[] nums) {
		this.nums = nums;
		map = new HashMap<>();
	}
	public boolean hasNext() {
		return index < nums.length;
	}

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
	public void skip(int num) {
		map.put(num, map.getOrDefault(num, 0)+1);
		checkSkipped();
	}
}
