
class PeekingIterator implements Iterator<Integer> {
    public Iterator<Integer> iterator; // declare a Iterator of type integer.
	Integer current; // Create an Integer variable.

	public PeekingIterator(Iterator<Integer> iterator) {
	   this.iterator = iterator; // Initialize variables.
        
        if(iterator.hasNext())
            current = iterator.next();
	}

  
	public Integer peek() { // returns the current element.
        return current;
	}

	
	@Override
	public Integer next() {     // returns the next element.
	    Integer result = current;
        
        if(iterator.hasNext()) // Check if the iterator has any element left.
            current = iterator.next(); // assign current to next element.
        else
            current = null; // else current will become null.
        return result;
	}

	@Override
	public boolean hasNext() {
	    if(current==null) return false;
        else
            return true;
	}
}