// Time Complexity : hasNext() -> O(1), next() -> O(N)
// Space Complexity : O(N) N = no of skips
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class SkipIterator implements Iterator<Integer> {
    Integer nextEl;
    HashMap<Integer, Integer> map;
    Iterator<Integer> iter;

    public SkipIterator(Iterator<Integer> it) {
        this.iter = it;
        nextEl = null;
        map = new HashMap<>();
        advance();
    }

    //O(N)
    private void advance() {
        nextEl = null;
        while (iter.hasNext()) {
            Integer temp = iter.next();
            if (map.containsKey(temp)) {
                map.put(temp, map.get(temp) - 1);
                if (map.get(temp) == 0) {
                    map.remove(temp);
                }
            } else {
                nextEl = temp;
                break;
            }
        }
    }

    //O(1)
    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    //O(N)
    @Override
    public Integer next() {
        Integer res = nextEl;
        advance();
        return res;
    }

    //O(N)
    public void skip(int num) {
        if (nextEl == num) {
            advance();
        } else {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8).iterator());
        System.out.println(it.hasNext());   // true
        it.skip(5);//
        System.out.println(it.next());      //6
        it.skip(5);
        System.out.println(it.next());      // 7
        System.out.println(it.next());      // 6
        it.skip(8);                    //
        it.skip(9);                    //
        System.out.println(it.next());      // 5
        System.out.println(it.next());      // 5
        System.out.println(it.next());      // 6
        System.out.println(it.hasNext());   // true
        System.out.println(it.next());      // 8
        System.out.println(it.hasNext());   // false
    }
}