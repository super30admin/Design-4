//                    hasNext            next              skip
// Time Complexity:     O(1)         Aux O(1)          Aux O(1)
// Space Complexity:    O(1)             O(1)              O(1)

class SkipIterator implements Iterator<Integer> {
    
    Map<Integer, Integer> map;
    Integer nextEle;
    Iterator<Integer> it;

	public SkipIterator(Iterator<Integer> it) {
        this.map = new HashMap<>();
        this.nextEle = null;
        this.it = it;
        advance();
	}

	public boolean hasNext() {
        return nextEle != null;
	}

	public Integer next() {
        Integer result = nextEle;
        advance();
        return result;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(nextEle == val) {                                 // if current element needs to be skipped
            advance();
        }
        else {
            map.put(num, map.getOrDefault(num, 0)+1);        // if element needs to be skipped in future
        }
	}
    
    private void advance() {
        nextEle = null;
        while(nextEle == null && it.hasNext()) {             // iterate till we get element that not needs to be skipped and there are elements left in iterator
            Integer ele = it.next();                         // get current element
            if(map.containsKey(ele)) {                       // if in map, needs to be skipped
                map.put(ele, map.get(ele)-1);                // update count
                map.remove(ele, 0);                          // remove if needed
            }
            else {                                           // got element that not needs to be skipped
                nextEle = ele;
            }
        }
    }
}

// // ******************** Sample Case ********************
// SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
// itr.hasNext(); // true
// itr.next(); // returns 2
// itr.skip(5);
// itr.next(); // returns 3
// itr.next(); // returns 6 because 5 should be skipped
// itr.next(); // returns 5
// itr.skip(5);
// itr.skip(5);
// itr.next(); // returns 7
// itr.next(); // returns -1
// itr.next(); // returns 10
// itr.hasNext(); // false
// itr.next(); // error
