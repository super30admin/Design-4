
// Time Complexity : O(1)
// Space Complexity :O(1)
// Did this code successfully run on Leetcode : Yes
// Three line explanation of solution in plain english
// have a hashmap of remove number and its count. and have a nextEl pointer to point to the next value; while in the skip if next pointing to same then move using advance function; In the advncae function get new element from iterator and check if hm contians it? if yes then remove decrease the count and do again, else set the nextEl to that number
// Your code here along with comments explaining your approach
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

public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }
}