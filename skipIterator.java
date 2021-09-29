// https://leetcode.com/discuss/interview-question/341818

class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> it;
    Map<Integer, Integer> map;
    Integer nextEl;
    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        map = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        int temp = nextEl;
        advance();
        return temp;
    }

    public void skip(int num) {
        if(num!=nextEl) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        } else {
            advance();
        }
    }

    private void advance() {
        nextEl = null;
        while(nextEl==null && it.hasNext()) {
            Integer curr = it.next();
            if(map.containsKey(curr)) {
                map.put(curr, map.getOrDefault(curr,0) - 1);
                map.remove(curr,0);
            } else {
                nextEl = curr;
            }
        }
    }
}

public class Main {
        public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6).iterator());
        System.out.println(it.hasNext());
        it.skip(5);
        System.out.println(it.next());
        it.skip(7);
        it.skip(5);
        it.skip(8);
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
    }
}