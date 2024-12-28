//Tc: O(1), O(k) when k consecutive elements needs to be skipped
//SP: O(n): to store n number of elements to be skipped
class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nit; // native itertaor
    HashMap<Integer,Integer> skipMap; // to store future skip elements
    Integer nextEl;
	public SkipIterator(Iterator<Integer> it) {
        this.nit=it;
        this.skipMap = new HashMap<>();
        advance(); // if you get the first call as skip itsefl
	}

	public boolean hasNext() {
        return nextEl!=null; // O(1)
	}

	public Integer next() { // O(n)
        Integer temp = nextEl; // store previous next element in a temp
        advance(); // sets the nextElement to valid nextElement
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val==nextEl){
            advance(); // then clear the nextElement and set a proper nextElement
        }else{
            skipMap.put(val,skipMap.getOrDefault(val,0)+1);// if it is a future Element then store in map
        }
	}

    public void advance(){
        // find the new nextElement of Skip Iterator
        // first the prev nextElement is declared null
        this.nextEl = null; // clear
        while(it.hasNext()){ // identify the valid next Element with help of native iterator
            Integer el = it.next(); // get elment from original iterator
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