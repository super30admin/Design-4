// Time Complexity : O(n) for advance function and O(1) for rest all the function.
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
// "static void main" must be defined in a public class.
class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    HashMap<Integer,Integer> memoryMap;
    Integer nextEl;
	public SkipIterator(Iterator<Integer> it) {
    memoryMap =  new HashMap<>();    
    this.it = it;
    advance();
	}

	public boolean hasNext() {
        
        return nextEl!=null;
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
        
        if(nextEl == val)
        {
            advance();
        }
        else
        {
            memoryMap.put(val, memoryMap.getOrDefault(val,0)+1);
        }
	}
    
    private void advance()
    {
        nextEl = null;
        while(nextEl == null && it.hasNext())
        {
            Integer el = it.next();
            if(memoryMap.containsKey(el))
            {
                memoryMap.put(el, memoryMap.getOrDefault(el,0)-1);
                memoryMap.remove(el,0);
            }
            else
            {
                nextEl = el;
            }
        }
    }

}




public class Main {
   public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        itr.hasNext(); // true
      
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
        itr.hasNext(); // false
         System.out.println(itr.next()); // error
    }
}