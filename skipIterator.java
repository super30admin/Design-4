
// Time Complexity : O(N)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

import java.util.*;
class SkipIterator implements Iterator<Integer> {
    
    Map<Integer, Integer> skipMap;
    Iterator<Integer> it;
    Integer nextEl;
    
	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        skipMap = new HashMap<>();
        advance();
	}

	public boolean hasNext() {
        return nextEl != null;
	}

	public Integer next() {
        Integer temp = nextEl;
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
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
	}
    
    private void advance(){
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer el = it.next();
            if(!skipMap.containsKey(el)){
                nextEl = el;
            }
            else{
                skipMap.put(el, skipMap.get(el) - 1);
                if(skipMap.get(el) == 0){
                    skipMap.remove(el);
                }
            }
        }
    }
    
    public static void main(String[] args) {
	    ArrayList<Integer> arrlist = new ArrayList<Integer>();

	    arrlist.add(2);
	    arrlist.add(3);
	    arrlist.add(5);
    	arrlist.add(6);
	    arrlist.add(5);
	    arrlist.add(7);
	    arrlist.add(5);
    	arrlist.add(-1);
	    arrlist.add(5);
	    arrlist.add(10);
	    ListIterator<Integer> iterator = arrlist.listIterator();
	    SkipIterator itr = new SkipIterator(iterator);

	    System.out.println(itr.hasNext()); // true
	    itr.skip(2); // returns 2
	    itr.skip(5);
	    System.out.println(itr.next()); // returns 3
	    System.out.println(itr.next());  // returns 6 because 5 should be skipped
	    System.out.println(itr.next());  // returns 5
	    itr.skip(5);
    	itr.skip(5);
	    System.out.println(itr.next());  // returns 7
	    System.out.println(itr.next());  // returns -1
    	System.out.println(itr.next());  // returns 10
	    itr.hasNext(); // false
    	System.out.println(itr.next());  // error
}
}