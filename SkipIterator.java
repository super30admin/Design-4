/***
 * TC - O(n)
 * SC - O(n)
 * */

import java.util.*;

class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> it;
    Map<Integer, Integer> map;

    Integer value;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        map = new HashMap<>();
        value = null;
    }

    public boolean hasNext() {
        if(value == null && !it.hasNext()) return false;
        if(value == null) value = it.next();
        checkSkipped();
        return value != null;
    }

    public Integer next() {
        if(value == null) value = it.next();
        checkSkipped();
        int result = value;
        value = null;
        return result;
    }

    public void checkSkipped() {
        while(value != null && map.containsKey(value)) {
            map.put(value, map.get(value) -1);
            if(map.get(value) == 0)
                map.remove(value);
            value = it.hasNext()? it.next() : null;
        }
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        map.put(val, map.getOrDefault(val, 0) +1);
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(new Integer[]{2, 3, 5, 6, 5, 7, 5, -1, 5, 10, 5, 10});
        SkipIterator itr = new SkipIterator(list.iterator());
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
        System.out.println(itr.next()); // error
    }
}