// Time Complexity :O(numner of skip numbers)
// Space Complexity :O(number of skip number)
// Did this code successfully run on Leetcode :Yes
// Any problem you faced while coding this :NO


// Your code here along with comments explaining your approach

import java.util.*;
class SkipIterator implements Iterator<Integer> {
    List<Integer> set= new ArrayList<>();
    Iterator<Integer> iter;
	public SkipIterator(Iterator<Integer> it) {
        iter=it;
	}

	public boolean hasNext() {
            return iter.hasNext();
	}

	public Integer next() {
        int next= iter.next();
            for(int s: set){
                if(next==s){
                    set.remove(new Integer(s));
                    return iter.next();
                }
            }
            return next;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        set.add(val);
	}


    public static void main(String args[]){
        int [] myArray= new int[]{2, 3, 5, 6, 5, 7, 5, -1, 5, 10};
        SkipIterator itr = new SkipIterator(Arrays.stream(myArray).iterator());
        
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // error
    }
}
