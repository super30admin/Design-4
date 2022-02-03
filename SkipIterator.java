// Time Complexity: O(n)
// Space Complexity: O(n)
class SkipIterator implements Iterator<Integer> {
    Map<Integer, Integer> map;
    Integer nextElement;
    Iterator<Integer> it;

	public SkipIterator(Iterator<Integer> it) {
        this.map = new HashMap<>();
        this.it = it;
        advance();// set postion of next element
	}

    // position nextElement
    private void advance() 
    {
        nextElement = null;
        while(nextElement == null && it.hasNext())
        {
            int curr = it.next();
            // if present in map advance and reduce
            if(map.containsKey(curr))
            {
                map.put(curr, map.getOrDefault(curr, 0) - 1);
                map.remove(curr, 0); // remove if 0
            }
            else
            nextElement = curr;
        }
    }

	public boolean hasNext() {
        return nextElement != null;
	}

	public Integer next() {
        int temp = nextElement;
        advance();
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val == nextElement)
            advance();
        else
        {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
	}
}
