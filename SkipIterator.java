/*
Time Complexity:

Constructor: O(k) for the initial call to advance(), where k is the number of skipped elements at the start.
advance(), next(): O(k) per call, where k is the number of skipped elements encountered.
hasNext(), skip(int val): O(1) for most calls, with O(k) in the worst case for skip(int val) if advance() is called.
                          O(k) if all the elements are skipped initially.Else it would be averagely O(1) if skip
                          elements are random.

Space Complexity: O(n), where n is the number of unique elements being skipped.
*/
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

// "static void main" must be defined in a public class.

class SkipIterator implements Iterator<Integer>
{
    HashMap<Integer, Integer> skipMap; //Stores the upcoming skip element with its frequency
    Iterator<Integer> nit; //nativeIterator
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it)
    {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance(); //calling the advance function in constructor so initial or first elements can be handled for skip
    }

    private void advance()
    {
        /*
        clear the nextEl, set it to null
        using native iterator, try to find out if there is any valid nextEl that is not
        present in skipMap, if the valid value element is found, store the next valid value in nextEl
        */
        nextEl = null;
        while(nit.hasNext())
        {
            Integer el = nit.next();
            if(skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el)-1); //get the value, decrement the freq and put it back
                skipMap.remove(el, 0); //remove the entry if the freq becomes zero
            }
            else {
                nextEl = el;
                break;
            }
        }
    }

    public boolean hasNext()
    {
        return nextEl != null;
    }

    public Integer next()
    {
    /*
    store the nextEl in temp variable, call advance function so it sets the nextEl to val is nextEl and then return
    temp
    */
        Integer temp = nextEl;
        advance();
        return temp;
    }

    public void skip(int val)
    {
        /*
            if this value is equal to the nextEl which we are ready to give out, basically the curr element then call
            advance() this will clear the nextEl and set the valid one
        */
        if(val == nextEl) {
            advance();
        }
        else {
         /*
            If the skipElement is the future element, just increment the freq in skipMap if present or else add a
            new entry in skipmap
        */
            skipMap.put(val, skipMap.getOrDefault(val, 0)+1);
        }
    }
}
class Main {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());
        System.out.println(itr.hasNext()); // true
        itr.skip(5);
        System.out.println(itr.next()); //6
        itr.skip(5);
        System.out.println(itr.next());  //7
        System.out.println(itr.next());  //6
        itr.skip(8);
        itr.skip(9);
        System.out.println(itr.next());  //5
        System.out.println(itr.next());  //5
        System.out.println(itr.next());  //6
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next());  //8
        System.out.println(itr.hasNext()); // false
    }
}
