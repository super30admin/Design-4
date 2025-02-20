public class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> ints;
    Integer next;
    Map<Integer, Integer> map = new HashMap<>();

    public SkipIterator(Iterator<Integer> it) {
        this.ints = it;
        advance();
    }

    public boolean hasNext() {
        return next != null;
    }

    public Integer next() {
        Integer el = next;
        advance();
        return el;
    }

    public void advance() {
        next = null;
        while (next == null && ints.hasNext()) {
            int el = ints.next();
            if (map.containsKey(el)) {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            } else {
                next = el;
            }
        }
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (next == val) {
            advance();
        } else {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
    }
}
