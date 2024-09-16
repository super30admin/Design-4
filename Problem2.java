class SkipIterator implements Iterator<Integer> {
    
    Map<Integer,Integer> skipMap;
    Iterator <Integer> it;
    int nextEle;
	public SkipIterator(Iterator<Integer> it) {
        skipMap = new HashMap<Integer,Integer>();
        this.it = it;
        advance()
	}

	public boolean hasNext() {
        return nextEle != null;//check if we have next Element
	}

	public Integer next() {
        //get the next element and move iterator by 1
        int temp = nextEle;
        advance();
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        //check if next element is val. Skip it else have a reference in map which can bee used later
        if (nextEle == val)
            advance();
        else
            map.put(val,map.getOrDefault(val,0)+1);
	}
    
    public void advance() {
        nextEle = null;
        //rmeove the current mapping and find the neext element which is not in skipList and iterator is not empty
        while(nextEle == null && it.hasNext()) {
            int next = it.next();
            if(map.containsKey(next)){
                map.put(next,map.get(next)-1);
                map.put(next,0);
            }
            else
                nextEle = next;
        }
    }
}
