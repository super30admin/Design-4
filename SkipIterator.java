import java.util.*;
class SkipIterator implements Iterator<Integer> {
Integer nextEl;
HashMap<Integer,Integer> skipMap;
Iterator<Integer> it;
	public SkipIterator(Iterator<Integer> it) {
	    this.it =it;
	    this.skipMap=new HashMap<>();
	    advance();
	}
	
	private void advance()
	{
	    this.nextEl = null;
	    while(nextEl == null && it.hasNext())
	    {
	        Integer curEl = it.next();
	        if(!skipMap.containsKey(curEl))
	        {
	            nextEl=curEl;
	        }
	        else{
	            skipMap.put(curEl,skipMap.get(curEl)-1);
	            skipMap.remove(curEl,0);
	        }
	    }
	}

	public boolean hasNext() {
	    return nextEl!=null;
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
	    if(val == nextEl)
	    {
	        advance();
	    }
	    else{
	        skipMap.put(val, skipMap.getOrDefault(val,0)+1);
	    }
	}
}
public class Main
{
	public static void main(String[] args) {
	SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5); // Do not print skip, it returns void
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns 5
        itr.skip(5); // Do not print skip, it returns void
        itr.skip(5); // Do not print skip, it returns void
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
	}
}

/* 
Time Complexity:
Constructor: O(n), where n is the number of elements in the iterator.
hasNext(): O(1).
next(): O(n) (in the worst case, if it needs to skip many elements).
skip(int val): O(n) (in the worst case, if it needs to skip many elements).
advance(): O(n) (in the worst case, iterating over all remaining elements).

Space Complexity:
O(k), where k is the number of unique elements that need to be skipped (the size of the skipMap).
*/