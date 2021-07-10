import java.util.*;
class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> it;
    Integer nextEl;
    HashMap<Integer, Integer> map;
	public SkipIterator(Iterator<Integer> it) {
        this.map = new HashMap<>();
        this.it = it;
        advance();
	}

	public boolean hasNext() {
        return nextEl != null;
	}

	public Integer next() {
        int temp = nextEl;
        advance();
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val == nextEl){
            advance();
        } else {
            map.put(val, map.getOrDefault(val, 0) +1);
        }
	}

    private void advance() {
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            int el = it.next();
            if(!map.containsKey(el)){
                nextEl = el;
            }
            else{
                map.put(el, map.get(el)-1);
                map.remove(el, 0);
            }
        }
    }
}