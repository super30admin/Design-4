// "static void main" must be defined in a public class.

class SkipIterator implements Iterator<Integer> {
    
        // Time Complexity : O(n) for advance method only  
// Space Complexity : O(n)  for advance method only
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :


 // Your code here along with comments explaining your approach
 /*
 1. Maintain HashMap with skip value and its count 
 2. use advance method to maintain the nextElement any time.
 */
    
    Iterator<Integer> it;
    HashMap<Integer,Integer> hm;
    int nextEl;
	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        hm = new HashMap<Integer,Integer>();
        advance();
	}

	public boolean hasNext() {
        if(nextEl!= null) return true;
        else return false;
	}

	public Integer next() {
        int temp = nextEl;
        advance();
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
       if(val == nextEl){
           advance();
       }
       else{
           hm.put(val, hm.get(val)+1);
       }
	}
    
    private void advance(){
        nextEl = null;
        if(nextEl == null && it.hasNext()){
            el = it.next();
            if(!hm.containsKey(el)){
                nextEl = el;
            }
            else{
                hm.get(el, hm.get(el)-1);
                hm.remove(el,0);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}