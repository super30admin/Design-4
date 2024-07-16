class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> skippedElements = new HashMap<>(); // to store skipped elements -> 1 : 4 -> 1 to be skipped 4 times
    Iterator<Integer> iterator; // iterator
	public SkipIterator(Iterator<Integer> it) {
        this.iterator = it; // initialise iterator
	}

    @Override
	public boolean hasNext() {
        return iterator.hasNext();
	}

    @Override
	public Integer next() {
        int nextElement = -1;
        if (!iterator.hasNext()) {
            return -1;
        }
        nextElement = iterator.next();
        if (skippedElements.containsKey(nextElement)) { // check to see if there is an entry of a skip in the map
            skippedElements.put(nextElement, skippedElements.get(nextElement)-1); // decrease it
            if (skippedElements.get(nextElement) == 0) { // need not skip it anymore
                skippedElements.remove(nextElement); // remove from map
            }
            if (!iterator.hasNext()) return -1;
            return iterator.next();
        }
        return nextElement;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        skippedElements.put(val, skippedElements.getOrDefault(val, 0)+1); // adding an entry to skip map
	}
}