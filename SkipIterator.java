import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class SkipIterator {
    Iterator<Integer> it; //native iterator
    HashMap<Integer, Integer> map; //map stores the frequency count of skip element
    Integer nextEl; //global nextEl stores the next of the native iterator

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();
        nextEl = it.next(); //initialize nextEl with the first element of input iterator
    }

    public boolean hasNext() { //O(1)TC
        return nextEl != null;
    }

    public Integer next() { //O(1)TC
        Integer current = nextEl; //store the global nextEl in current to not lose it
        advance(); //check if the upcoming next is being skipped or not
        return current; //and the return the current which is actual next
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) { //O(s) S.C, where s is no of elements being skipped
        if(val == nextEl) advance(); //if the current global nextEl is the one to be skipped
        else {
            if(!map.containsKey(val))  //check if the skip map already contains the input val
            map.put(val, map.getOrDefault(val, 0) + 1); //increment the value of this input in the map
        }
    }

    public void advance() { //T.C -> O(1) amortized, O(n) W.C, where n is the count of elements in iterator
        nextEl = null; //make the nextEl as null
        while(nextEl == null && it.hasNext()) { //while it is null and native iterator has a next
            Integer current = it.next(); //assign the next to the current
            if(!map.containsKey(current)) nextEl = current; //if the skip map doesn't contain this current,
            //make the nextEl as current
            else { //if it does contain
                map.put(current, map.get(current) - 1); //decrement the value of current in skip map
                if(map.get(current) == 0) map.remove(current); //and remove it completely if its frequency becomes 0
            }
        }
    }

    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3, 4, 2, 5).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        System.out.println(it.next());
        it.skip(1);
        it.skip(3);
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
    }
}