// Problem 2 : Skip Iterator
// Time Complexity : 
'''
hasNext() - O(1)
next() - O(n) where n is the number of elements in the iterator
skip() - O(n) where n is the number of elements in the iterator
'''
// Space Complexity : O(n) where n is the number of elements in the iterator and this will be size of the map
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :
/*
None
*/

// Your code here along with comments explaining your approach

class SkipIterator implements Iterator<Integer> {
    // defining the iteratorm skipMap(store frequency of the skip element) and next element of the iterator
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> skipMap;
    private Integer nextElement;

	public SkipIterator(Iterator<Integer> it) {
        // initializing the iterator, skipMap
        this.it = it;
        this.skipMap = new HashMap<>();
        // calling advance function to set the next element of the iterator
        advance();
	}
    
    private void advance() {
        // set the next element to null first
        nextElement = null;
        // loop till the next element is null and iterator has next element
        while(nextElement == null && this.it.hasNext()) {
            // store the next element of iterator in temprorary variable
            int temp = this.it.next();
            // check if there is an entry in skipMap for the element
            if(!skipMap.containsKey(temp)) {
                // if it is not present then set the nextElement variable with this value
                nextElement = temp;
            } else {
                // else decrement the value of the temp variable in skipMap
                skipMap.put(temp, skipMap.get(temp) -1);
                // if the value is zero after decrementing then remove the entry from skipMap
                skipMap.remove(temp, 0);
            }
        }
    }

	public boolean hasNext() {
        // return the true if the nextElement is not null or else it will return false
        return nextElement != null;
	}

	public Integer next() {
        // check first hashNext if it return false then throw an exception of empty iterator
        if(!hasNext()) throw new RuntimeException("empty");
        // get the value of nextElement first 
        Integer element = nextElement;
        // if it is then call advance which will set the nextElement to valid next element of the iterator
        advance();
        return element;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        // check first hashNext if it return false then throw an exception of empty iterator
        if(!hasNext()) throw new RuntimeException("empty");
        // check if the val is equal to nextElement
        if(nextElement == val) {
            // if it is then call advance which will set the nextElement to valid next element of the iterator
            advance();
        } else {
            // else increment the value of the val in skipMap
            skipMap.put(val, skipMap.getOrDefault(val, 0)+1);
        }
        
	}
}



public class Main {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
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
        System.out.println(itr.next()); // error// "static void main" must be defined in a public class.*/
    }
}