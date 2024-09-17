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
        Integer el = nextEl;
        advance();
        System.out.println(el);
        return el;
    }

    public void skip(int num) {
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

public class Main {
        public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext()); // true

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

// Time Complexity: O(n), n=number of elements
// Space Complexity: O(n), n= number of elements