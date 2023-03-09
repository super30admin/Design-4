import java.util.HashMap;
import java.util.Iterator;

// "static void main" must be defined in a public class.
class SkipIterator implements Iterator<Integer> {
	HashMap<Integer, Integer> skipMap = new HashMap<>();
	Integer nextEle;
	Iterator<Integer> nit;

	public SkipIterator(Iterator<Integer> it) {
		advance();
	}

	public void advance() {
		this.nextEle = null;
		while (this.nit.hasNext() && this.nextEle == null) {
			Integer el = nit.next();
			if (!skipMap.containsKey(el)) {
				this.nextEle = el;
			} else {
				skipMap.put(el, skipMap.get(el) - 1);
				skipMap.remove(el, 0);
			}
		}
	}

	public boolean hasNext() {
		return this.nextEle != null;
	}

	public Integer next() {
		Integer res = this.nextEle;
		advance();
		return res;
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
			skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
		}
	}
}