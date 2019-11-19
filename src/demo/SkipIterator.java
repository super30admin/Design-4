package demo;

import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {

	public SkipIterator(Iterator<Integer> it) {
	}

	public boolean hasNext() {
		return false;
	}

	public Integer next() {
		return null;
	}

	
	public void advance() {
		
	}
	
	public void skip() {
		
	}
	/**
	* The inpu t parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
	}
}