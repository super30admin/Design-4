// Time Complexity : o(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : N/A
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    HashMap<Integer,Integer> hmap;
    Integer nextElement;

	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.hmap = new HashMap<>();
        //call advance if not nextelem will be null
        advance();
	}

	public boolean hasNext() {
        return nextElement !=null;
	}

	public Integer next() {
        Integer temp = nextElement;
        //next valid number os skip ietrator
        advance();
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int num) {
        if(num == nextElement) 
         advance();
        else
         hmap.put(num,getOrDefault(num,0)+1);
	}

    public void advance(){
        nextElement = null;
        while(nextElement ==null && it.hasNext()){
            Integer el = it.next();
            if(hmap.containsKey(el)){
                hmap.put(el,hmap.getOrDefault(el,0)-1);
                hmap.remove(el,0);
            }
            else{
                nextElement = el;
            }
        }
    }
}
