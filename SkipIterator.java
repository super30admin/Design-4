/** Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.
* TC O(N) - Iterate through all the elements of the inputs. SC O(N) - In worst case, all elements could be skipped and they will end up in hashmap
*/
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class SkipIterator implements Iterator<Integer> {

	private final Iterator<Integer> it;
	HashMap<Integer, Integer> counter;
	Integer nextEl;
	public SkipIterator(Iterator<Integer> it) {
		this.it = it;
		counter = new HashMap<>();
		nextEl = null;
		skipEle();
	}

	public boolean hasNext() {
		skipEle();
		return (nextEl != null);
	}

	public Integer next() {
		skipEle();
		return nextEl;
	}
	
	private void skipEle() {
		nextEl = null;
		while (nextEl == null && it.hasNext()) {
			Integer ele  = it.next();
			if (counter.containsKey(ele)) {
				int val = counter.get(ele);
				val --;
				if (val > 0) {
					counter.put(ele, val);
				}
				else {
					counter.remove(ele);
				}
			}
			else {
				nextEl = ele;
			}
		}
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
		if (!counter.containsKey(val)) {
			counter.put(val, 0);
		}
		int value = counter.get(val);
		value++;
		counter.put(val, value);
	}
	
	public static void main(String[] args) {
	    SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
	    System.out.println(it.hasNext());
	    it.skip(2);
	    it.skip(1);
	    it.skip(3);
	    System.out.println(it.hasNext());
	}
}

