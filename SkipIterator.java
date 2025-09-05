//approach->here we are using the native iterator to iterate the list and store the nextElement that should be returned in a nextEl variable,
//and we are maintaining a map with frequency of elements need to be skipped
//whenever hasNext() is called we check nextEl's value and when next() is called we before we return nextEl we load it with the next element
//we update the nextEl with advance() and in advance we check if that element from the nativeIterator.next() is in the skip freq if it is we recursively call advance() till nextEl is updated ot we dont have any elements to load
//if skip()is called on the element stored in the nextEl, we call advance to load up nextEl with proper element
//time complexity->hasNext()->O(1) , next(), skip() runs in amortised O(1)
//space Complexity->O(n) n being the size of the list being iterated.
class SkipIterator implements Iterator<Integer> {
    Integer nextEl;
    Iterator<Integer> it;
    HashMap<Integer, Integer> freqMap;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;

        this.freqMap = new HashMap<>();
        advance();
    }

    public boolean hasNext() {

        return nextEl != null;
    }

    public Integer next() {
        int el = nextEl;
        advance();
        return el;
    }

    private void advance() {
        this.nextEl = null;
        if (it.hasNext()) {
            nextEl = it.next();
            if (freqMap.containsKey(nextEl)) {
                freqMap.put(nextEl, freqMap.get(nextEl) - 1);
                if (freqMap.get(nextEl) == 0) freqMap.remove(nextEl);
                advance();
            }
        }
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (nextEl == val) advance();
        else {
            freqMap.put(val, freqMap.getOrDefault(val, 0) + 1);
        }
    }

}
