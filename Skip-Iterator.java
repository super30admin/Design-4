// Time Complexity : O(1)
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : Not on leetcode
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
// keep Map of values to skip with frequency.
// keep next elemnt to return in global variable;

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    Map<Integer, Integer> skipFreq;
    Integer next;
	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        skipFreq = new HashMap<>();
	}

	public boolean hasNext() {
        if(it.hasNext()){
            next = it.next();
            while(skipFreq.containsKey(next)){
                int f = skipFreq.get(next);
                if(f == 1) skipFreq.remove(next);
                skipFreq.put(next,f-1);
                if(it.hasNext()) next = it.next();
                else next = null;
            }
        }
        return next != null;
	}

	public Integer next() {
        Integer n = next;
        next = null;
        return n;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        skipFreq.put(val,skipFreq.getOrDefault(val,0)+1);
	}
}