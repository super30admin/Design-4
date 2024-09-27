// Time Complexity:
// Space Complexity:

class SkipIterator implements Iterator<Integer> {
    skipMap<Integer, Integer> skipskipMap;
    Iterator<Integer> it;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        skipskipMap = new HashskipMap<>();
        this.it = it;
        advance();
    }

    public boolean hasNext() {
        return nextEl != null;
    }

    public Integer next() {
        int temp = nextEl;
        advance();
        return temp;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(val == nextEl){
            advance();
        }
        else {
            skipMap.put(val, skipMap.getOrDefault(val, 0 ) + 1);
        }
    }

    // Puts the next pointer at the viable element in the iterator
    private void advance(){
        nextEl = null;  // makes the previous nextEl null
        while(nextEl == null && it.hasNext()){ // iterating till a valid value for nextEl
            int el = it.next(); // element from original iterator
            if(skipMap.containsKey(el)){ // if element present in skipskipMap
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);  // if element's count is becoming 0, remove it
            }
            else {
                nextEl = el; // if the element doesn't need to be skipped, set it to the nextEl of the skip iterator

            }
        }
    }
}
