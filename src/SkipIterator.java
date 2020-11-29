import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class SkipIterator implements Iterator<Integer> {

	HashMap<Integer, Integer> smap;
	Integer nextEle;
	Iterator<Integer> it;

	public SkipIterator(Iterator<Integer> it) {
		this.it = it;
		smap = new HashMap<Integer, Integer>();
		advance();
	}

	private void advance() {
		// TODO Auto-generated method stub
		nextEle = null;
		while (nextEle == null && it.hasNext()) {
			Integer validOrNot = it.next();
			if (!smap.containsKey(validOrNot)) {
				nextEle = validOrNot;
			} else {
				smap.put(validOrNot, smap.getOrDefault(validOrNot, 0) - 1);
				smap.remove(validOrNot, 0);
			}
		}
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return nextEle != null;
	}

	@Override
	public Integer next() {
		// TODO Auto-generated method stub
		Integer tem = nextEle;
//		nextEle = it.next();
		advance();
		return tem;
	}

	/**
	 * The input parameter is an int, indicating that the next element equals 'val'
	 * needs to be skipped. This method can be called multiple times in a row.
	 * skip(5), skip(5) means that the next two 5s should be skipped.
	 */
	public void skip(int val) {
		if (val == nextEle) {
			advance();
		} else {
			smap.put(val, smap.getOrDefault(val, 0) + 1);
		}

	}

	public static void main(String[] args) {
		ArrayList<Integer> arrlist = new ArrayList<Integer>();

		arrlist.add(2);
		arrlist.add(3);
		arrlist.add(5);
		arrlist.add(6);
		arrlist.add(5);
		arrlist.add(7);
		arrlist.add(5);
		arrlist.add(-1);
		arrlist.add(5);
		arrlist.add(10);
		ListIterator<Integer> iterator = arrlist.listIterator();
		SkipIterator itr = new SkipIterator(iterator);

		itr.hasNext(); // true
		itr.skip(2); // returns 2
		itr.skip(5);
		itr.next(); // returns 3
		itr.next(); // returns 6 because 5 should be skipped
		itr.next(); // returns 5
		itr.skip(5);
		itr.skip(5);
		itr.next(); // returns 7
		itr.next(); // returns -1
		itr.next(); // returns 10
		itr.hasNext(); // false
		itr.next(); // error
	}
}
