//Time Complexity: O(n) -> Linear
//Space Complexity: 0(1) -> storing in HashMap

class SkipIterator implements Iterator<Integer> {

    private final Iterator<Integer> it;             //regular iterator to iterate over the array
    private final Map<Integer, Integer> skipMap;    //to store keys to be skipped with value as the occurence 
    private Integer nextEl;                         //next element

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.skipMap = new HashMap<>();
        //to place next pointer to the next element
        advance();
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
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (!skipMap.containsKey(el)) {
                //if not in the map, simply increment
                nextEl = el;
            } else {
                //else check map, get the value and decrement it by 1
                skipMap.put(el, skipMap.get(el) - 1);
                //if value is 0 after decrementing -> remove
                skipMap.remove(el, 0);
            }
        }
    }
}