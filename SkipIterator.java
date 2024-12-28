//Tc: O(1), O(k) when k consecutive elements needs to be skipped
//SP: O(n): to store n number of elements to be skipped
class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    HashMap<Integer,Integer> skipMap;
    Integer nextEl;
	public SkipIterator(Iterator<Integer> it) {
        this.it=it;
        this.skipMap = new HashMap<>();
        advance();
	}

	public boolean hasNext() {
        return nextEl!=null; // O(1)
	}

	public Integer next() { // O(n)
        Integer temp = nextEl; // previous next element
        advance(); // get the correct of nextEl;
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val==nextEl){
            advance();
        }else{
            skipMap.put(val,skipMap.getOrDefault(val,0)+1);
        }
	}

    public void advance(){
        // find the new nextElement of Skip Iterator
        //first the prev nextElement is declared null
        this.nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer el = it.next(); // elment from original iterator
            if(skipMap.containsKey(el)){
                skipMap.put(el,skipMap.get(el)-1); // reducing the count of el which need to be skipped
                skipMap.remove(el,0);
            } else{
                nextEl = el;
            }
        }

    }
}

SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
itr.hasNext(); // true
itr.next(); // returns 2
itr.skip(5);
itr.next(); // returns 3
itr.next(); // returns 6 because 5 should be skipped
itr.next(); // returns 5
itr.skip(5);
itr.skip(5);
itr.next(); // returns 7
itr.next(); // returns -1
itr.next(); // returns 10
itr.hasNext(); // false
itr.next(); // error