package week7.day2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class SkipIterator1 implements Iterator<Integer> {
	Iterator<Integer> nit;
	HashMap<Integer, Integer> map;
	Integer NextElement;

	public SkipIterator1(Iterator<Integer> it) {
		this.nit = it;
		this.map = new HashMap<>();
		advance();
	}

	private void advance() { // O(n), O(1)-amortized
		this.NextElement = null;
		while (nit.hasNext() && NextElement == null) { // NextElement==null important, otherwise, it keeps running until
														// it reaches end
			Integer el = nit.next();
			if (map.containsKey(el)) { // future el
				map.put(el, map.get(el) - 1);
				map.remove(el, 0);
			} else
				NextElement = el;
		}

	}

	public boolean hasNext() { // O(1)
		return NextElement != null;
	}

	public Integer next() { // O(n), O(1)-amortized
		Integer currElement = NextElement;
		advance();
		return currElement;
	}

	/**
	 * The input parameter is an int, indicating that the next element equals 'val'
	 * needs to be skipped. This method can be called multiple times in a row.
	 * skip(5), skip(5) means that the next two 5s should be skipped.
	 */
	public void skip(int val) { // O(n), O(1)-amortized
		// current skip
		if (NextElement != val)
			map.put(val, map.getOrDefault(val, 0) + 1);// future skip
		else
			advance();

	}
}

public class SkipIterator {

	public static void main(String[] args) {
		SkipIterator1 itr = new SkipIterator1(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
		System.out.println(itr.hasNext());// true
		System.out.println(itr.next()); // returns 2
		itr.skip(5);
		System.out.println(itr.next());// returns 3
		System.out.println(itr.next());// returns 6 because 5 should be skipped
		System.out.println(itr.next());// returns 5
		itr.skip(5);
		itr.skip(5);
		System.out.println(itr.next());// returns 7
		System.out.println(itr.next());// returns -1
		System.out.println(itr.next());// returns 10
		System.out.println(itr.hasNext());// false
		System.out.println(itr.next());// null
	}

}
