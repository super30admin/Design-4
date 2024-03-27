// https://leetcode.com/discuss/interview-question/341818

//Solution Link
//https://leetcode.com/playground/2mzQnpXk

import java.util.*;

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Integer nextEl;
    Iterator<Integer> it;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (map.containsKey(el)) {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            } else
                nextEl = el;
        }
    }

    @Override
    public Integer next() {
        Integer res = nextEl;
        advance();
        return res;
    }

    public void skip(int num) {
        if (nextEl != num) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        } else
            advance();
    }

}

/*
 * public class Main {
 * public static void main(String[] args) {
 * SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
 * System.out.println(it.hasNext());
 * it.skip(2);
 * it.skip(1);
 * it.skip(3);
 * System.out.println(it.hasNext());
 * }
 * }
 * 
 */