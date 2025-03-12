// Time Complexity : written below for all the methods.
// Space Complexity : O(1) as we are using iterators, and hashmap would contain atmost 10 key-value pairs so its O(1).
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
import java.util.*;

class SkIterator{
    Iterator<Integer> skipIterator;
    HashMap<Integer, Integer> skipfreqMap;
    Integer nextElement;
    public SkIterator(Iterator<Integer> it) {
        skipIterator = it;
        nextElement = null;
        skipfreqMap = new HashMap<>();
        this.advance();
    }

    private void advance()
    {
        nextElement = null;
        while(nextElement == null && skipIterator.hasNext())
        {
            Integer elem = skipIterator.next();
            if(!skipfreqMap.containsKey(elem))
            {
                nextElement = elem;
                return;
            }
            else{
                skipfreqMap.put(elem, skipfreqMap.get(elem)-1);
                if(skipfreqMap.get(elem) == 0)
                    skipfreqMap.remove(elem);
            }
        }
    }
    //Time Complexity : O(1)
    public boolean hasNext() {
        return nextElement != null;
    }

    //Time Complexity : O(n)
    public Integer next() {
        if(!hasNext()) return null;
        int temp = nextElement.intValue();
        advance();
        return temp;
    }

    /**
    * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
    * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
    */ 
    //Time Complexity : O(n)
    public void skip(int val) {
        if(!hasNext()) return;
        if(nextElement.intValue() == val)
            advance();
        else
            skipfreqMap.put(val, skipfreqMap.getOrDefault(val, 0)+1);
    }
}

public class SkipIterator
{
    public static void main(String[] args)
    {
        List<Integer> lst = new ArrayList<>();
        lst.add(2);
        lst.add(3);
        lst.add(5);
        lst.add(6);
        lst.add(5);
        lst.add(7);
        lst.add(5);
        lst.add(-1);
        lst.add(5);
        lst.add(10);
        

        SkIterator itr = new SkIterator(lst.iterator()); 
        
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
    }
}
