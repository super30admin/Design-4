class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> it;
    private Map<Integer, Integer> skipMap;  // Stores values to skip and their frequencies
    private Integer nextEl;  // Stores the next valid element
    
    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.skipMap = new HashMap<>();
        advance();  // Move to the first valid element
    }
    
    // Advances to the next valid element, skipping if necessary
    private void advance() {
        nextEl = null;
        while (it.hasNext()) {
            Integer el = it.next();
            if (skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el) - 1);
                if (skipMap.get(el) == 0) {
                    skipMap.remove(el);
                }
            } else {
                nextEl = el;
                break;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        if (nextEl == null) {
            throw new IllegalStateException("No more elements.");
        }
        Integer result = nextEl;
        advance();  // Move to the next valid element for subsequent calls
        return result;
    }

    // Add a value to be skipped in future iterations
    public void skip(int val) {
        if (nextEl != null && nextEl == val) {
            advance();  // If the current element is the one to be skipped, advance
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }
}
