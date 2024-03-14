class SkipIterator implements Iterator<Integer> {

	unordered_map<int,int> m;
	Iterator<Integer> iterator;
	int next_ele;

	public SkipIterator(Iterator<Integer> it) {
		iterator = it;
		if(iterator.hasNext())  next_ele = iterator.next();
		else  next_ele = -1;
	}

	public boolean hasNext() {
		return next_ele != -1;
	}

	public Integer next() {
		int ret = next_ele; //store return value before moving next_ele

		while(iterator.hasNext()){
			next_ele = iterator.next(); 

			if(m.count(next_ele)==0 || m[next_ele]==0)  break; //skip check
			m[next_ele]--; //skipped once
			
			next_ele = -1; //if iterator exhausts, next_ele should be -1
		}

		return ret;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
		m[val] = m.count(val) ? m[val]+1 : 1;
	}
}
