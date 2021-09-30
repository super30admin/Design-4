import java.util.*;

class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> it;
    HashMap<Integer, Integer> map;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        Integer temp = nextEl;
        advance();
        return temp;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped. This method can be called multiple times in a row.
     * skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int num) {
        if (num == nextEl) {
            advance();
        } else {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
    }

    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (map.containsKey(el)) {
                map.put(el, map.getOrDefault(el, 0) - 1);
                map.remove(el, 0);
            } else {
                nextEl = el;
            }
        }
    }
}
