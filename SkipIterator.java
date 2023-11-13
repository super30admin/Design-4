import java.util.*;

/*
* Strategy:
* 1. skip: add the current element to cap of elem -> #timesToSkip. If the currently pointed element is same as the elem to skip then advance to next valid position.
* 2. hasNext: our aim is to either advance when we call hasNext and next to next valid position or do it before hand. If here we move to next position, we would have iterate the exisiting collection using its iterators next(), but then we will loose current valid element. So rather save the element which are accessing for that case. But as we will see below we dont need to actually advance in has Next bcoz we will do that in skip and next methods. Thus our pointer (if not null) has the next valid value.
* 3. next: we are saving the current element pointed. Then advance to next valid position. As this advancing will cause us to update the currently pointed elem.
* 4. advance: This method keep moving ahead until the next of original iterator returns us a valid value. Then as this value will be lost, we store it in variable which will be accessed by skip and has next"

* Time:
* skip: O(n)
* hasNext: O(1)
* next: O(n)

* Tested: Yes
 */
class SkipIterator implements Iterator<Integer> {
	private Iterator<Integer> it;
	private HashMap<Integer, Integer> skipMap;
	private Integer currElem;

	public SkipIterator(Iterator<Integer> it) {
		this.it = it;
		this.skipMap = new HashMap<>();
		currElem = it.next();
	}


	public boolean hasNext() {
		return (currElem != null);
	}


	public Integer next() {
		Integer rInteger = currElem;
		advance();
		return rInteger;
	}


	/**

	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.

	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.

	*/ 

	public void skip(int val) {
		
		if(currElem == null) {
			return;
		}
		if(val == currElem) {
			advance();
		}
		else {
			skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
		}
	}

	private void advance() {
		currElem = null;
		while (it.hasNext()) {
			currElem = it.next();
			if(skipMap.getOrDefault(currElem, 0) > 0) {
				skipMap.put(currElem, skipMap.get(currElem)-1);
			}
			else {
				break;
			}
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
	}

}