// Time Complexity : O(n), where n is the number of elements in the iterator.
// Space Complexity : O(n), where n is the size of the map
// Did this code successfully run on Leetcode : N/A
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
/*
1. Keep track of current element and store it in a variable.
2. If the skip value is equal to the current element, then advance the iterator.
3. Else, store the skip value in a map with its count.
4. If the map contains the current element, then decrement its count and remove it from the map if the count is 0.
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {
    Integer currEl;
    Iterator<Integer> it;
    HashMap<Integer, Integer> skipMap;
    
	public SkipIterator(Iterator<Integer> it) {
        this.skipMap = new HashMap<>();
        this.it = it;	
        advance();
    }

    private void advance(){
        currEl = null;
        while(currEl == null && it.hasNext()){
            Integer el = it.next();
            if(!skipMap.containsKey(el)){
                currEl = el;
            } else {
                skipMap.put(el, skipMap.get(el) - 1);
                if(skipMap.get(el) == 0){
                    skipMap.remove(el);
                }
            }
        }
    }

    @Override
	public boolean hasNext() { //O(1)
        return currEl != null;
	}

    @Override
	public Integer next() { //O(n)
        Integer el = currEl;
        advance();
        return el;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {//O(n)
        if(currEl == val){
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
	}
}

class Main {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next());; // returns 3
        System.out.println(itr.next());; // returns 6 because 5 should be skipped
        System.out.println(itr.next());; // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next());; // returns 7
        System.out.println(itr.next());; // returns -1
        System.out.println(itr.next());; // returns 10
        System.out.println(itr.hasNext());; // false
    }
}