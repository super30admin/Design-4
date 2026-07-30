import java.util.*;

class SkipIterator implements Iterator<Integer> {
    /*
        next()
            TC: O(1) amortized, Each element from the underlying iterator is visited only once.
        skip(int val)
                TC: O(1)
        SC -  O(k), where k is the number of values waiting to be skipped.

        Maintain a HashMap to store values that need to be skipped and their counts.
        Keep a buffered next element so that hasNext() does not advance the iterator.
        Whenever we fetch the next element:
        If it exists in the skip map, decrease its skip count and continue searching.
        Otherwise, store it as the next valid element.
    */

    private Iterator<Integer> it;
    private Map<Integer, Integer> skipMap;
    private Integer nextElement;
    private boolean hasNextElement;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    private void advance() {
        hasNextElement = false;

        while (it.hasNext()) {
            int val = it.next();

            if (skipMap.containsKey(val)) {
                int count = skipMap.get(val);

                if (count == 1) {
                    skipMap.remove(val);
                } else {
                    skipMap.put(val, count - 1);
                }

                continue;
            }

            nextElement = val;
            hasNextElement = true;
            break;
        }
    }

    @Override
    public boolean hasNext() {
        return hasNextElement;
    }

    @Override
    public Integer next() {
        if (!hasNextElement) {
            throw new NoSuchElementException();
        }

        int result = nextElement;
        advance();
        return result;
    }

    public void skip(int val) {
        if (hasNextElement && nextElement == val) {
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }
}
