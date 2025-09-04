  import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// This solution uses native iterator to iterate through the elements at the same time using a hashmap to store the upcoming elements which needs to be skipped. We can maintain a count for each element that is to make sure multiple counts are handled. 
class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> it;
    Integer nextElement;
    Map<Integer, Integer> map;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        map = new HashMap<>();
        advance();
    }

    // common method to advance the elements by skipping the available list and decrementing count whenever skipped.
    private void advance() {
        nextElement=null;
        while(it.hasNext()) {
            int val = it.next();
            if(map.containsKey(val)) {
                map.put(val, map.get(val)-1);
                if(map.get(val)==0) {
                    map.remove(val);
                }
            } else {
                nextElement = val;
                // If element is found, we should be breaking
                break;
            }
        }
    }

    public boolean hasNext() {
        advance();
        return nextElement==null;
    }

    public Integer next() {
        int val = nextElement;
        advance();
        return val;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(!map.containsKey(val)) {
            map.put(val, 0);
        }
        map.put(val, map.get(val)+1);
    }

    public static void main(String args[])
    {
        int[] arr = {2, 3, 5, 6, 5, 7, 5, -1, 5, 10};
        SkipIterator itr = new SkipIterator(Arrays.stream(arr).iterator());
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
