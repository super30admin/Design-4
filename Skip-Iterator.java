// https://leetcode.com/discuss/interview-question/341818

class SkipIterator implements Iterator<Integer> {
    Integer nextEl;
    HashMap<Integer, Integer> skipMap;
    Iterator<Integer> it;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.skipMap = new HashMap<>();
        advance();
    }
    
    private void advance() { //O(n)
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);
                nextEl = el;
            } else {
                nextEl = el;
            }
        }
    }
    
    public void skip(int num) { //O(n)
        if (nextEl == num) {
            advance();
        } else {
            skipMap.put(num, skipMap.getOrDefault(num, 0) + 1);
        }
    }

    @Override
    public boolean hasNext() { //O(1)
        return nextEl != null;
    }

    @Override
    public Integer next() { //O(n)
        int temp = nextEl;
        advance();
        return temp;
    }
}