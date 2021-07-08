import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class SkipIterator implements Iterator<Integer> {

	Integer nextEle;
	Iterator<Integer> it;
	Map<Integer, Integer> skipMapCount;

	public SkipIterator(Iterator<Integer> it) {
		this.it = it;
		skipMapCount = new HashMap<Integer, Integer>();
		moveToNext();
	}

	public boolean hasNext() {
		return nextEle != null;
	}

	public Integer next() {
		Integer tmp = nextEle;
		moveToNext();
		return tmp;
	}

	/**
	 * The input parameter is an int, indicating that the next element equals 'val'
	 * needs to be skipped. This method can be called multiple times in a row.
	 * skip(5), skip(5) means that the next two 5s should be skipped.
	 */
	public void skip(int val) {
		if (nextEle == val) {
			moveToNext();
		} else {
			skipMapCount.compute(val, (k, v) -> v == null ? 1 : v + 1);
		}
	}

	private void moveToNext() {
		nextEle = null;
		while (nextEle == null && it.hasNext()) {
			Integer tmp = it.next();
			if (skipMapCount.containsKey(tmp)&&skipMapCount.get(tmp)>0) {
				skipMapCount.compute(tmp, (k, v) -> v - 1);
			} else {
				nextEle = tmp;
			}
		}
	}

	public static void main(String[] args) {
		SkipIterator itr = new SkipIterator(Arrays.stream(new int[] { 2, 3, 5, 6, 5, 7, 5, -1, 5, 10 }).iterator());
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
