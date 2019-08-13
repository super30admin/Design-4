// Time Complexity : O(n) , as we are looping through all elements
// Space Complexity : O(n) , as we are storing all the elements.

// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
class PeekingIterator implements Iterator<Integer> {

    Queue<Integer>  l = new LinkedList<Integer>();
    
	public PeekingIterator(Iterator<Integer> iterator) {
	    // initialize any member here.
        while(iterator.hasNext()){
            l.add(iterator.next());  // adding the elements in the Queue.
        }
	    
	}

    // Returns the next element in the iteration without advancing the iterator.
	public Integer peek() {
        
        return l.peek();
        
	}

	// hasNext() and next() should behave the same as in the Iterator interface.
	// Override them if needed.
	@Override
	public Integer next() {
        
	    return l.poll();
	}

	@Override
	public boolean hasNext() {
	    boolean queueEmpty = l.isEmpty();
        return !queueEmpty;
	}
}
