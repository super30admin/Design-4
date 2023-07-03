
import java.util.*;
class SkipIterator implements Iterator<Integer> {
	private Iterator<Integer> it;
	private Integer nextEl;
	private Map<Integer, Integer> skipMap;


	public SkipIterator(Iterator<Integer> it) {
		this.it = it;
		this.skipMap = new HashMap<>();
		advance();
	}


	public boolean hasNext() {
		return nextEl != null;
	}

	public Integer next() {
		Integer temp = nextEl;
		advance();
		return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
		if(val != nextEl){
			skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
		}else{
			advance();
		}
	}

	private void advance(){
		nextEl = null;
		while(nextEl == null && it.hasNext()){
			Integer curr = it.next();
			if(skipMap.containsKey(curr)){
				skipMap.put(curr, skipMap.get(curr) - 1);
				skipMap.remove(curr, 0);
			}else{
				nextEl = curr;
			}
		}
	}
	public static void main(String[] args) {
		SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
		// new SkipIterator(Arrays.asList(1, 2, 3).iterator());
		System.out.println(itr.hasNext());// true
		System.out.println(itr.next());// returns 2
		itr.skip(5);
		System.out.println(itr.next()); // returns 3
		System.out.println(itr.next()); // returns 6 because 5 should be skipped
		System.out.println(itr.next()); // returns 5
		itr.skip(5);
		itr.skip(5);
		System.out.println(itr.next()); // returns 7
		System.out.println(itr.next()); // returns -1
		System.out.println(itr.next()); // returns 10
		System.out.println(itr.hasNext()); // false
		System.out.println(itr.next()); // error
	}
}


