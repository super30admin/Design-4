//  Time Complexity: O(n)
//  Space Complexity: O(n)

import java.util.*;

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> iter;
    Integer nextElement;
    Map<Integer, Integer> skipMap;

    public SkipIterator(Iterator<Integer> it) {
        this.iter = it;
        skipMap = new HashMap<>();
        advance();
    }

    public boolean hasNext() {
        return nextElement != null;
    }

    public Integer next() {
        Integer temp = nextElement;
        advance();
        return temp;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */

    public void skip(int val) {

        if (val == nextElement) {
            advance();
        }
        else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);  //  future value
        }

        if (skipMap.get(val) == 0) {
            skipMap.remove(val);
        }
    }

    private void advance() {
        nextElement = null;
        while (iter.hasNext() && nextElement == null) {
            Integer element = iter.next();

            //  is it a valid next element?
            if (skipMap.containsKey(element)) {
                skipMap.put(element, skipMap.get(element) - 1);
            }
            else {
                nextElement = element;
            }
        }
    }
}

