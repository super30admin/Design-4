
//TC: O(n)
//SC: O(n)
class SkipIterator implements Iterator<Integer> {
   Iterator nextEl;
   Iterator<Integer> nIt;
   HashMap<Integer, Integer> skipMap;
	public SkipIterator(Iterator<Integer> it) {
         this.nIt = it;
         this.skipMap = new HashMap<>();
         advance();
	}

private void advance(){//O(n)
    this.nextEl = null;
     while(nextEl == null && this.nIt.hashNext()){
           Integer el = nIt.next();
           if(skipMap.containsKey(el))
       		skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el,0);
             }else{
	this.nextEl = el;
}
}

	public boolean hasNext() { //O(1)
        return nextEl != null;
	}

	public Integer next() { //O(1)
      Integer re = nextEl;
     advance();
     return re;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int num) {  //O(n)
     if(num == nextEl) advance();
     else{
       skipMap.put(num,skipMap.getOrDefault(num,0) + 1);
      }
	}
}