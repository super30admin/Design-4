import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

//Time complexity is O(N)
//Space complexity is O(N)

public class BigN80SkipIterator {
	static class SkipIterator implements Iterator<Integer> {
		Iterator<Integer> it;
		HashMap<Integer, Integer> skipMap;
		Integer nextElement;

		public SkipIterator(Iterator<Integer> it) {
			this.it = it;
			this.skipMap = new HashMap<>();
			advance();
		}

		private void advance() {
			nextElement = null;
			while (nextElement == null && it.hasNext()) {
				Integer el = it.next();
				if (skipMap.containsKey(el)) {
					skipMap.put(el, skipMap.get(el) - 1);
					skipMap.remove(el, 0); // (,0) -> remove if value is zero
				} else {
					nextElement = el;
				}
			}
		}

		public boolean hasNext() {
			return nextElement != null;
		}

		public Integer next() {
			Integer temp = nextElement;
			advance();
			return temp;
		}

		/**
		 * The input parameter is an int, indicating that the next element equals 'val'
		 * needs to be skipped. This method can be called multiple times in a row.
		 * skip(5), skip(5) means that the next two 5s should be skipped.
		 */
		public void skip(int val) {
			// pointer wala skip
			if (nextElement == val)
				advance();
			else {
				if (!skipMap.containsKey(val)) {
					skipMap.put(val, 0);
				}
				skipMap.put(val, skipMap.get(val) + 1);
			}
		}

	public static void main(String[] args) {
		SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
		System.out.println(itr.hasNext()); // true
		System.out.println(itr.next()); // returns 2
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
}