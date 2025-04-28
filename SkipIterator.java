// https://leetcode.com/discuss/interview-question/341818

// TC: O(1) hasNext, skip
// SC: O(k)
/*
This Java code implements a custom SkipIterator that allows skipping certain values
dynamically while iterating through a collection. It wraps a normal Iterator<Integer>,
using a HashMap (skipMap) to track how many times each value needs to be skipped.
The advance() method ensures that nextEl always points to the next valid (non-skipped)
element. When skip(val) is called, it either immediately advances if val is the next element
or records the skip for future encounters. The main method
 tests various skip and next operations to demonstrate how elements are skipped during iteration.
* */
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    HashMap<Integer,Integer> skipMap;
    Integer nextEl;
    public SkipIterator(Iterator<Integer> it) {
        this.it=it;
        skipMap=new HashMap<>();
        advance();

    }

    public boolean hasNext() {
        return nextEl!=null;
    }

    public Integer next() {
        Integer temp=nextEl;

        advance();
        return temp;
    }

    private void advance()
    {
        nextEl=null;
        while(it.hasNext())
        {
            Integer el=it.next();
            if(skipMap.containsKey(el))
            {
                skipMap.put(el,skipMap.get(el)-1);
                if(skipMap.get(el)==0)
                    skipMap.remove(el);
            }
            else
            {
                nextEl=el;
                break;
            }
        }
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(val==nextEl)
        {
            advance();
        }
        else
        {
            if(!skipMap.containsKey(val))
            {
                skipMap.put(val,1);
            }
            else
            {
                skipMap.put(val,skipMap.get(val)+1);
            }
        }

    }
}

public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());
        System.out.println(it.hasNext());
        it.skip(5);
        System.out.println(it.next());
        it.skip(5);
        System.out.println(it.next());
        System.out.println(it.next());
        it.skip(8);
        it.skip(9);
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());

    }
}