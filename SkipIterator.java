// https://leetcode.com/discuss/interview-question/341818

/*
Use a native iterator and keep the count of the skipped value. If we reach that value move native iterator to next and decrease count in fewq map
TC: O(1) for next() and skip() but O(1) amortized for hasNext()
SC: O(n)
*/
class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nit;
    HashMap<Integer, Integer> map;
    Integer nextEle;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.map = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEle != null;
    }

    @Override
    public Integer next() {
        Integer el = nextEle;
        advance();
        return el;
    }

    public void skip(int num) {
        if (nextEle == num) {
            advance();
        } else {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
    }

    private void advance() {
        nextEle = null;
        while (nextEle == null && nit.hasNext()) {
            Integer el = nit.next();
            if (!map.containsKey(el)) {
                nextEle = el;
            } else {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
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
