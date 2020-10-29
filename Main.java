// "static void main" must be defined in a public class.

import java.util.*;

class SkipIterator implements Iterator<Integer> {

    Integer curr;
    Iterator<Integer> it;
    HashMap<Integer, Integer> map;
	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        map = new HashMap<>();
        curr = it.next();
	}

	public boolean hasNext() {
        if (curr == null) {
            return false;
        }
        return true;
	}

	public Integer next() {
        if (curr == null) return curr;
        
        while (map.containsKey(curr)) {
            int count = map.get(curr);
            if (count == 1) {
                map.remove(curr);
            } else {
                map.put(curr, count - 1);
            }
            if (it.hasNext()) {
                curr = it.next();
            } else {
                curr = null;
                return null;
            }
        }
        Integer next = curr;

        if (it.hasNext()) {
            curr = it.next();
        } else {
            curr = null;
        }
        return next; 
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        int count = map.getOrDefault(val, 0);
        map.put(val, count + 1);
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
    System.out.println(itr.next()); // retturns 7
    itr.skip(5);
    System.out.println(itr.next()); // returns 7
    System.out.println(itr.next()); // returns -1
    System.out.println(itr.next()); // returns 10
    System.out.println(itr.hasNext()); // false
    System.out.println(itr.next()); // error
    }
}