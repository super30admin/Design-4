import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// Time Complexity : hasNext: O(1) next:o(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this :



// Your code here along with comments explaining your approach
/* 
at the start we make store 1st element as nextElement
if the skipped element is the current stored next Element then 
we move ahead using our native iterator and assign nextElement as iterator.next();

We maintain a map to save all the skip elements that we might encounter in future
and with element we also save it's frequency
each time we skip the element we reduce the frequency from the map
*/
public class SkipIterator implements Iterator<Integer>{
    Iterator<Integer> it;
    Integer nextElement;
    Map<Integer,Integer> skips;
    SkipIterator(Iterator<Integer> it)
    {
        this.it=it;
        skips=new HashMap<>();
        advance();
    }
    
    @Override
    public boolean hasNext() {
        System.out.println(nextElement!=null);
        return nextElement!=null;
    }

    @Override
    public Integer next() {
        Integer temp= nextElement;
        advance();
        System.out.println(temp);
        return temp;
    }

    public void advance()
    {
        nextElement=null;

        while(nextElement==null && it.hasNext())
        {
            Integer e1=it.next();
            if(!skips.containsKey(e1))
            nextElement=e1;
            else{
                skips.put(e1, skips.get(e1)-1);
                skips.remove(e1,0);
            }
        }

    }
    /**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val==nextElement)
        advance();
        else skips.put(val, skips.getOrDefault(val, 0)+1);
	}

    public static void main(String args[])
    {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
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
