// TC = hasNext() = O(1), next()= O (n), because of the advance function - O(n)

class SkipIterator implements Iterator<Integer> {
	Iterator  <Integer> nit;
	Integer nextEl;
	HashMap <Integer, Integer> skipMap;

	public SkipIterator(Iterator<Integer> it) {
		this.nit = it;
		this.skipMap = new HashMap <>();
		advance ();
	}

	public void advance(){
		this.nextEl = null;
		while(nit.hasNext() && this.nextEl == null){
			Integer el = nit.next();
			if(!skipMap.containsKey(el)){
				this.nextEl = el;
			}
			else{
				skipMap.put(el, skipMap.get(el) - 1);
				skipMap.remove(el, 0);
			}
		}

	}

	public boolean hasNext() {
		return nextEl != null;
	}

	public Integer next() {
		Integer re = this.nextEl;
		advance();
		return re;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
		if(val == nextEl){
			advance();
		}
		else{
			skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
		}
	}
}


public class Main {
	public static void main(String [] args){
		SkipIterator it = new SkipIterator (Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

		System.out.println(it.hasNext());
		it.skip(5);
		System.out.println(it.next());
		it.skip(5);

		System.out.println(it.next());
		System.out.println(it.next());
		it.skip(8);
		it.skip(9);

		System.out.println(it.next());
		System.out.println(it.next());
		System.out.println(it.hasNext());
		System.out.println(it.next());

		System.out.println(it.hasNext());

	}
}


