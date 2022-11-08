package s30.Design-4;
//Question:- Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.
 
class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> skippedElements = new HashMap<>();
    Iterator<Integer> iterator;
	public SkipIterator(Iterator<Integer> it) {
        this.iterator = it;
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
        if (skippedElements.containsKey(nextElement)) {
            skippedElements.put(nextElement, skippedElements.get(nextElement)-1);
            if (skippedElements.get(nextElement) == 0) {
                skippedElements.remove(nextElement);
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
        skippedElements.put(val, skippedElements.getOrDefault(val, 0)+1);
	}
}
}
