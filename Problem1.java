// skip iterator

// Time Complexity : O(1)
// Space Complexity : O(N)

class SkipIterator implements Iterator<Integer> {
HashMap<Integer,Integer> map;
Iterator<Integer> itr;
Integer nextEl;
	public SkipIterator(Iterator<Integer> it) {
		map=new HashMap<>();
		itr=it;
		advance();
	}

    @Override
	public boolean hasNext() {
		return nextEl!=null;
	}

     @Override
	public Integer next() {

			Integer temp= nextEl;
			advance();
			return temp;

	}

	public void skip(int val) {
	    if(nextEl==val)	advance();
		if(!map.containsKey(val)) {
			map.put(val,0);
		}

		map.put(val,map.get(val)+1);


	}

	public void advance() {
	    nextEl=null;
			while(nextEl==null && itr.hasNext()) {
		    int el=itr.next();
			if(map.containsKey(el) ){
				if(map.get(el)==1) {
					map.remove(el);
				}else {
					map.put(el,map.get(el)-1);
				}
			}else {
				nextEl=el;
			}
		}

	}
}