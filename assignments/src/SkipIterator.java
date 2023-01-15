import java.util.*;

public class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> nit;
    Map<Integer, Integer> skipMap;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        skipMap = new HashMap();
        advance();
    }

    // O(n)
    private void advance() {
        this.nextEl = null;
        while (nextEl == null && nit.hasNext()) {
            int el = nit.next();
            if (skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);                      // removes the element if value = 0
            }
            else {
                nextEl = el;
            }
        }
    }

    // O(1)
    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    // O(n)
    @Override
    public Integer next() {
        Integer res = nextEl;
        advance();
        return res;
    }

    // O(n)
    public void skip(int num) {
        if (num == nextEl) {
            advance();
        }
        else {
            skipMap.put(num, skipMap.getOrDefault(num, 0) + 1);
        }
    }

    public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

        System.out.println(it.hasNext());   // true
        it.skip(5);
        System.out.println(it.next());      // 6
        it.skip(5);
        System.out.println(it.next());      // 7
        System.out.println(it.next());      // 6
        it.skip(8);
        it.skip(9);
        System.out.println(it.next());      // 5
        System.out.println(it.next());      // 5
        System.out.println(it.next());      // 6
        System.out.println(it.next());      // 8
        System.out.println(it.hasNext());   // false
    }
}