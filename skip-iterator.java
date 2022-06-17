// Time Complexity : O(n)
// Space Complexity :constant 
// Did this code successfully run on Leetcode :yes


class SkipIterator implements Iterator<Integer> {
    public Integer nextEl;
    public HashMap<Integer, Integer> map;
    public Iterator<Integer> it;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();
        advance();
    }

    // we will keep traversing until we find next valid element
    public void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (map.containsKey(el)) {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            } else {
                nextEl = el;
            }
        }
    }

    // if we need to skip current element ie. next Element to be returned, we'll
    // advance as it is possible that
    // next elements are already in hashmap ie. to be skipped
    public void skip(Integer n) {
        if (nextEl == n) {
            advance();
        } else {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
    }

    // we'll just return next element and move our pointer to next valid element
    public Integer next() {
        Integer result = nextEl;
        advance();
        return result;
    }

    public boolean hasNext() {
        return nextEl != null;
    }
}

public class Main {

    public static void main(String[] args) {
        int[] it = { 5, 6, 7, 5, 6, 8, 9, 5, 5, 6 };
        SkipIterator si = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6).iterator());
        // System.out.println("has nextel"+si.hasNext());
        System.out.println(" nextel" + si.next());
        si.skip(6);
        si.skip(7);
        si.skip(9);
        si.skip(5);
        System.out.println("has nextel" + si.hasNext());
        System.out.println(" nextel" + si.next());
        System.out.println("Hello World!");
    }
}