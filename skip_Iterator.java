    /*  Explanation
    # Leetcode problem link : https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator
    Time Complexity for operators : o(n^n) .. n is the length of the string
    Extra Space Complexity for operators : o(n) for (List<String> path) without recursive stack
    Did this code successfully run on Leetcode : NA
    Any problem you faced while coding this : No
# Your code here along with comments explaining your approach
        # Basic approach : 
        # Optimized approach: 
                              
            # 1. 
                    A) 
    */  
import java.util.*;
class SkipIterator implements Iterator<Integer> {
    HashMap<Integer,Integer> hm;
	public SkipIterator(Iterator<Integer> it) {
        hm = new HashMap<>();
        
    }

	public boolean hasNext() {

	}

	public Integer next() {
        
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(hm.containsKey(val)){
            hm.put(val,hm.get(val)+1);
        }else
            hm.put(val,1);
	}

    public static void main(String args[]){
        SkipIterator itr = new SkipIterator(new Iterator(2, 3, 5, 6, 5, 7, 5, -1, 5, 10));
        itr.hasNext(); // true
        itr.next(); // returns 2
        itr.skip(5);
        itr.next(); // returns 3
        itr.next(); // returns 6 because 5 should be skipped
        itr.next(); // returns 5
        itr.skip(5);
        itr.skip(5);
        itr.next(); // returns 7
        itr.next(); // returns -1
        itr.next(); // returns 10
        itr.hasNext(); // false
        itr.next(); // error
    }
}