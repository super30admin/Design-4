package design4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class SkipIterator implements Iterator<Integer> {
	//Time Complexity : Defined on every method
	//Space Complexity : O(n), where n is the number of skip elements
	//Did this code successfully run on Leetcode : Yes
	//Any problem you faced while coding this : No
	public static void main(String[] args) {
		SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6).iterator());

		System.out.println(it.hasNext());
		it.skip(6);// 6 --nextEl
		it.skip(5);// 6
		System.out.println(it.next()); //7
		it.skip(5);
		System.out.println(it.next());// 6
		System.out.println(it.next()); // 8
		it.skip(5);
		it.skip(8);

		System.out.println(it.next());

		System.out.println(it.next());
		it.skip(6);
		System.out.println(it.hasNext());
		System.out.println(it.next());
		System.out.println(it.next());
		it.skip(1);
		it.skip(3);

		System.out.println(it.next());
	}

	Map<Integer, Integer> skipMap;
	Integer nextEl;
	Iterator<Integer> it;
	public SkipIterator(Iterator<Integer> it) {
		skipMap = new HashMap<>();
		this.it = it;
		advance();
	}

	// O(1)
	@Override
	public boolean hasNext() {
		return nextEl != null;
	}

	// O(n)
	@Override
	public Integer next() {
		Integer temp = nextEl;
		advance();
		return temp;
	}

	// O(n)
	public void skip(int val) {
		if(nextEl == null)
			return;
		else if(nextEl == val)
			advance();
		else
			skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
	}

	// Worst - O(n), Average - O(1)
	public void advance() {
		nextEl = null;
		while(nextEl == null && it.hasNext()) {
			Integer el = it.next();
			if(skipMap.containsKey(el)) {
				skipMap.put(el, skipMap.get(el) - 1);
				if(skipMap.get(el) == 0)
					skipMap.remove(el);
			} else 
				nextEl = el;
		}
	}
}
