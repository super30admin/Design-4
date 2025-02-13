// Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.
// Time Complexities:
// hasNext() - O(1)
// next() - O(n)
// skip() - O(n)

// Space Complexity: O(n) 


import java.util.*;

class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> it;
    HashMap<Integer, Integer> map;
    Integer pointer;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();
        if (it.hasNext())
            pointer = it.next();
        else
            pointer = null;
    }

    private void proceedToNextValidPosition() {
        while (pointer != null) {
            Integer currVal = null;
            if (it.hasNext())
                currVal = it.next();
            if (currVal != null && map.containsKey(currVal)) {
                map.put(currVal, map.get(currVal) - 1);
                if (map.get(currVal) == 0)
                    map.remove(currVal);
            } else {
                pointer = currVal;
                break;
            }
        }
    }

    public boolean hasNext() {
        System.out.println(pointer != null);
        return pointer != null;
    }

    public Integer next() {
        if (pointer == null) {
            System.out.println("Error");
            return -1;
        }
        Integer ans = pointer;
        proceedToNextValidPosition();
        System.out.println(ans);
        return ans;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (pointer == null) {
            System.out.println("Empty");
            return;
        }
        if (pointer == val)
            proceedToNextValidPosition();
        else
            map.put(val, map.getOrDefault(val, 0) + 1);
    }
}

public class CustomSkipIterator {
    public static void main(String a[]) {
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
