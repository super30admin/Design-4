package demo;

import java.util.*;


class SkipIterator implements Iterator<Integer> {
	
	Iterator<Integer> it ;
	Map<Integer, Integer> skipMap;
	Integer nextElement;
	

	public SkipIterator(Iterator<Integer> it) {
		this.it = it;
		this.skipMap = new HashMap<Integer, Integer>();
		advance();
	}

	@Override
	public boolean hasNext() {
		return nextElement != null;
	}

	@Override
	public Integer next() {
		Integer temp = nextElement;
		advance();
		return temp;
	}

	
	public void advance() {
		nextElement = (Integer) null;
		while(nextElement ==null && it.hasNext() ) {
			Integer e1 = nextElement;
			
			if(skipMap.containsKey(e1)) {
				nextElement = e1;
				
			}
			else {
				skipMap.put(e1, skipMap.get(e1) - 1);
				//when count reaches zero, remove from map
				skipMap.remove(e1,0);
			}
		}
	}
	

	/**
	* The inpu t parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
		if(nextElement == val) {
			advance();
			
		}else {
			if(skipMap.containsKey(val)) {
				skipMap.put(val, 0);
			}else {
				skipMap.put(val, skipMap.get(val)+1);
			}
		}
	}
}