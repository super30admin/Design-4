/**
 * findNextElement: O(n)
 * hasNext: O(1)
 * next: O(n)
 * skip: O(n)

 Did this code successfully run on Leetcode : yes
 Any problem you faced while coding this :
 */

import java.util.*;
public class SkipIterator implements Iterator<Integer> {

    private Iterator<Integer> iterator;
    private Map<Integer, Integer> skipMap;
    private Integer nextElement;

    public SkipIterator(Iterator<Integer> values) {
        this.iterator = values;
        skipMap = new HashMap<>();
        findNextElement();
    }

    private void findNextElement() {
        nextElement = null;
        while(iterator.hasNext()) {
            Integer current = iterator.next();
            if(skipMap.containsKey(current)) {
                skipMap.put(current, skipMap.get(current) - 1);
                if(skipMap.get(current) == 0) {
                    skipMap.remove(current);
                }
            }
            else {
                nextElement = current;
                break;
            }
        }
    }

    public boolean hasNext() {
        return nextElement != null;

    }

    public Integer next() {
        Integer result = nextElement;
        findNextElement();
        return result;
    }

    public void skip(int val) {
        if(nextElement != null && nextElement == val) {
            findNextElement();
        }
        else {
            skipMap.put(val, skipMap.getOrDefault(val, 0)+1);
        }
    }


    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
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