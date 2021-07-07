class SkipIterator implements Iterator<Integer> {

	public SkipIterator(Iterator<Integer> it) {
            this.it=it;
	}
        Iterator<Integer> it ;
        HashMap<Integer,Integer> skips=new HashMap<>();
        Integer nextEl=it.next();
	public boolean hasNext() {
            return nextEl!=null;
	}

	public Integer next() {
            while(it.hasNext()){
                 if(skips.keySet().contains(nextEl)){
                     it.next();
                     int skipValLeft =skips.get(nextEl)-1;
                     if(skipValLeft==0) skips.remove(nextEl);
                     else skips.put(nextEl, skipValLeft);
                 }else{
                     break;
                 }
            }
            return nextEl;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {

            if(val==nextEl){
               nextEl= it.next();
            }else{
                if(!skips.containsKey(val)){
                    skips.put(val,0);
                }
                skips.put(val,skips.get(val)+1);
            }
                
	}
}
