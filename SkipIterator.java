class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> count;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.count = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        if (!hasNext()) throw new RuntimeException("empty");
        Integer el = nextEl;
        advance();
        return el;
    }

    public void skip(int num) {
        if (!hasNext()) throw new RuntimeException("empty");
        if (nextEl == num) {
            advance();
        } else {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }
    }

    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (!count.containsKey(el)) {
                nextEl = el;
            } else {
                count.put(el, count.get(el) - 1);
                count.remove(el, 0);
            }
        }
    }
}

/*
 * Time Complexity:
 * - hasNext(): O(1) → Simply checks if nextEl is not null.
 * - next(): O(1) amortized → The advance() method may loop through skipped elements, but each element is only processed once.
 * - skip(int num): O(1) → HashMap put and get operations are O(1) average.
 * - advance(): O(1) amortized → Each element is either assigned to nextEl or skipped exactly once.

 * Space Complexity:
 * - O(n) for the HashMap (where n is the number of unique elements to be skipped).
 * - O(1) for the nextEl variable.
 * - Overall space complexity: O(n) where n is the maximum number of unique elements to skip.
 */