import java.util.*;
class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> skipMap;
    Integer nextEl;
    Iterator<Integer> nit;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    public void advance() {
        this.nextEl = null;
        while (nit.hasNext()) {
            Integer el = nit.next();
            if (!skipMap.containsKey(el)) {
                nextEl = el;
                break;
            } else {
                skipMap.put(el, skipMap.get(el) - 1);
                if (skipMap.get(el) == 0) {
                    skipMap.remove(el);
                }
            }
        }
    }

    public boolean hasNext() {
        return nextEl != null;
    }

    public Integer next() {
        Integer el = nextEl;
        advance();
        return el;
    }

    public void skip(int val) {
        if (val == nextEl) {
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8).iterator());
        System.out.println(itr.hasNext());
        itr.skip(5);
        System.out.println(itr.next());
        itr.skip(5);
        System.out.println(itr.next());
        System.out.println(itr.next());
        itr.skip(8);
        itr.skip(9);
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
    }
}



