// Time Complexity :O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : 


// Your code here along with comments explaining your approach

// "static void main" must be defined in a public class.
class SkipIterator implements Iterator<Integer> {

    HashMap<Integer, Integer> skipMap;
    Iterator<Integer> it;
    Integer nextel;

	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        skipMap = new HashMap<>();
        advance();
	}


	public boolean hasNext() {
        return nextel != null;
	}
    
	public Integer next() {
        int temp = nextel;
        advance();
        return temp; 
	}



	/**

	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.

	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.

	*/ 

	public void skip(int val) {
        if(val==nextel){
            advance();
        }
        else{
                skipMap.put(val,skipMap.getOrDefault(val,0)+1);
        }
	}
    
    private void advance(){
        nextel=null;
        while(nextel==null && it.hasNext()){
            Integer el = it.next();
            if(!skipMap.containsKey(el)){
                nextel = el;
            }
            else{
                skipMap.put(el,skipMap.get(el)-1);
                skipMap.remove(el,0);
            }
        }
    }
     

}
    