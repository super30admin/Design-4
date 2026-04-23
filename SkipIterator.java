import java.util.*;

// TC: next(), skip() : Amortized O(1) / hasNext(): O(1)
// SC: O(k) where k is the number of skipped elements tracked in skipMap

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nativeIterator;
    Integer nextElem;
    Map<Integer, Integer> skipMap;
    
	public SkipIterator(Iterator<Integer> it) {
        this.nativeIterator = it;
        this.skipMap = new HashMap<>();
        advance();
	}
    
    private void advance() {
        nextElem = null;
        while (nativeIterator.hasNext()) {
            nextElem = nativeIterator.next();
            if (!skipMap.containsKey(nextElem)) {
                break;
            }
            else {
                skipMap.put(nextElem, skipMap.get(nextElem)-1);
                skipMap.remove(nextElem, 0);
            }
        }
    }

	public boolean hasNext() {
        return nextElem != null;
	}

	public Integer next() {
        Integer temp = nextElem;
        advance();
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if (nextElem == val) {
            advance();
        }
        else {
            skipMap.put(val, skipMap.getOrDefault(val,0)+1);
        }
	}
}
