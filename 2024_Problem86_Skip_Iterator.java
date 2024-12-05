//https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator
//Time Complexity: O(n) - Linear
//Space Complexity: 0(1) - storing in HashMap

class SkipIterator implements Iterator<Integer> {

    private final Iterator<Integer> nativeIterator;             //native iterator
    private final Map<Integer, Integer> skipMap;    //to store future skip elements
    private Integer nextEl;                         //next element

    public SkipIterator(Iterator<Integer> it) {
        this.nativeIterator = it;
        this.skipMap = new HashMap<>();
        //to set the proper next element
        advance(); //in case first element needs to be skipped
    }

    @Override
    public boolean hasNext() {
        //if no element present -> false
        //else true
        return nextEl != null;
    }

    @Override
    public Integer next() {
        int temp = nextEl;
        advance();          //to find new next element
        return temp;        //to get prev number
    }

    public void skip(int val) {
        if (val == nextEl) {
            advance();
        } else {
            //put -> if doesnot exist with 1 value
            //else get the value, and increment it by 1
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }

    private void advance() {
        nextEl = null; //clar nextEl
        while (nextEl == null && nativeIterator.hasNext()) {
            Integer el = nativeIterator.next();
            if (!skipMap.containsKey(el)) {
                //if not in the map, simply increment
                nextEl = el;
                break;
            } else {
                //else check map, get the value and decrement it by 1
                skipMap.put(el, skipMap.get(el) - 1);
                //if value is 0 after decrementing -> remove
                skipMap.remove(el, 0);
            }
        }
    }
}