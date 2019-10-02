//Time complexity: O(1)
//Space complexity: O(N)
//Accepted on leetcode
class PeekingIterator implements Iterator<Integer> {
    Queue<Integer> list = new LinkedList<>();
	public PeekingIterator(Iterator<Integer> iterator) {
	    // initialize any member here.
	    while(iterator.hasNext())
        {
            list.add(iterator.next());
        }
	}

    // Returns the next element in the iteration without advancing the iterator.
	public Integer peek() {
        return list.peek();
	}

	// hasNext() and next() should behave the same as in the Iterator interface.
	// Override them if needed.
	@Override
	public Integer next() {
	    return list.poll();
	}

	@Override
	public boolean hasNext() {
	    return !list.isEmpty();
	}
}