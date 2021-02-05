// Time Complexity : O(n)
// Space Complexity : O(k)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach

class SkipIterator implements Iterator<Integer> {
    //create a hashmap 
    HashMap<Integer, Integer> hash;
    //have an iterator
    Iterator<Integer> it;
    //next element
    Integer nextEl;
	public SkipIterator(Iterator<Integer> it) {
        //intiate the hashmap
        hash = new HashMap<>();
        this.it = it;
        //call the advance funciton to move next element pointer
        advanced();
	}

	public boolean hasNext() {
        //check if the next element is null or not
        return nextEl != null;
	}

	public Integer next() {
        //store in result next elemnet 
        Integer res = nextEl;
        //move next to a valid next position
        advanced();
        return res;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        //if you have the same val as the next element just call advanced
        if(val == nextEl){
            advanced();
        }
        //or store the element in the map
        else{
            hash.put(val, hash.getOrDefault(val, 0) + 1);
        }
	}
    
    private void advanced(){
        //put next element at valid position
        nextEl = null;
        //while next element is null and iterator still has values then keep moving pointer
        while(nextEl == null && it.hasNext()){
            //store next element 
            Integer el = it.next();
            //check if the element is not in the map
            if(!hash.containsKey(el)){
                nextEl = el;
            }
            else{
                hash.put(el, hash.get(el)-1);
                hash.remove(el,0);
            }
        }
        
    }
}
