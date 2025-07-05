import java.util.HashMap;
import java.util.Iterator;

/*
 * The main intuition is to make use of the skip map and keep calling the native iterator. 
 * If it is inside the skip map then we'll reduce the count, if count is zero remove element from skip map. 
 * If not then I'll set it to nextEl data member of skip iterator
*/

public class SkipIterator implements Iterator<Integer> {
    Integer nextEl;
    Iterator<Integer> it;
    HashMap<Integer, Integer> skipMap;

    public SkipIterator(Iterator<Integer> iterator) {
        this.it = iterator;
        this.skipMap = new HashMap<>();
        advance();
    }

    private void advance() { // O(n)
        nextEl = null;
        while (it.hasNext()) {
            Integer el = it.next();
            if (skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el) - 1);
                if (skipMap.get(el) == 0) {
                    skipMap.remove(el);
                }
            } else {
                nextEl = el;
                return;
            }
        }
    }

    public void skip(int num) { // O(n)
        if (nextEl != null && nextEl.equals(num)) {
            advance();
        } else {
            skipMap.put(num, skipMap.getOrDefault(num, 0) + 1);
        }
    }

    @Override
    public boolean hasNext() { // O(1)
        return nextEl != null;
    }

    @Override
    public Integer next() { // O(n)
        Integer current = nextEl;
        advance();
        return current;
    }

    public static void main(String[] args) {
        Iterator<Integer> iterator = new Iterator<Integer>() {
            private int[] nums = { 1, 2, 3, 4, 5 };
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < nums.length;
            }

            @Override
            public Integer next() {
                return nums[index++];
            }
        };

        SkipIterator skipIterator = new SkipIterator(iterator);
        System.out.println(skipIterator.next()); // 1
        skipIterator.skip(2);
        System.out.println(skipIterator.next()); // 3
        skipIterator.skip(3);
        System.out.println(skipIterator.next()); // 4
    }
}
