// Time complexity - O(1) Space - O(N) N is the number of items to be skipped
class SkipIterator implements Iterator<Integer> {

	private Integer currPos;
	private final Map<Integer, Integer> skipCount;
	private final Iterator<Integer> it;

	public SkipIterator(Iterator<Integer> it) {
		skipCount = new HashMap<>();
		this.it = it;
	}

	public boolean hasNext() {
		return currPos != null;
	}

	public Integer next() {
		Integer val = currPos;
		seekCursor();
		return val;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
		if(currPos == val){
			seekCursor();
		}else{
			skipCount.put(val, skipCount.getOrDefault(val, 0) + 1);
		}
	}

	private void seekCursor(){
		currPos = null;

		while(it.hasNext()){
			Integer next = it.next();
			if(!skipCount.containsKey(next)){
				currPos = next;
				break;
			}else{
				skipCount.put(val, skipCount.get(val) - 1);
				// remove key from the map when the value reaches 0
				skipCount.remove(val, 0);
			}
        }
	}
}