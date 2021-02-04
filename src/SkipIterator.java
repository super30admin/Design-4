import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

// https://leetcode.com/discuss/interview-question/341818

/*

Design a SkipIterator that supports a method skip(int val). 
When it is called the next element equals val in iterator sequence should be skipped. 
If you are not familiar with Iterators check similar problems.

*/

class SkipIterator implements Iterator<Integer> {
	HashMap<Integer, Integer> map;
	Iterator<Integer> it;
	Integer nextEl;

	public SkipIterator(Iterator<Integer> it) {

		map = new HashMap<>();
		this.it = it;
		advance();

	}

	@Override
	public boolean hasNext() {

		return nextEl != null;

	}

	@Override
	public Integer next() {

		int result = nextEl;

		advance();

		return result;
	}

	public void skip(int num) {

		if (num == nextEl) {
			advance();
		} else {

			map.put(num, map.getOrDefault(num, 0) + 1);
		}

	}

	private void advance() {

		nextEl = null;

		while (nextEl == null && it.hasNext()) {

			Integer el = it.next();

			if (!map.containsKey(el)) {
				nextEl = el;
			} else {

				map.put(el, map.get(el) - 1);
				map.remove(el, 0);
			}

		}

	}

	public class Main {
		public void main(String[] args) {
			SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
			System.out.println(it.hasNext());
			it.skip(2);
			it.skip(1);
			it.skip(3);
			System.out.println(it.hasNext());
		}

	}

}
