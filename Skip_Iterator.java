import java.util.HashMap;

class SkipIterator implements Iterator<Integer> {
   Iterator<Integer> nativeIterator;
   Integer nextElement;
   HashMap<Integer, Integer> skipMap;
	public SkipIterator(Iterator<Integer> it) {
	 this.nativeIterator = it;
     this.skipMap = new HashMap<>();
     advance();

    
    }
    void advance() {
        this.nextElement = null;
        while(nativeIterator.hasNext() && nextElement == null) {
            Integer element = nativeIterator.next();
            if(skipMap.containsKey(element)) {
                skipMap.put(element,skipMap.get(element)-1);
                skipMap.remove(element,0);
            } else {
                nextElement = element;
            }
        }
    }

	public boolean hasNext() { //O(1) O(1)
        return nextElement != null;
	}

	public Integer next() { //O(n) O(1)
        Integer result = nextElement;
        advance();
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) { // O(n) O(1)
        if(nextElement != val) {
            skipMap.put(val,skipMap.getOrDefault(val, 0)+1); 
        } else {
            advance();
        }
	}
}