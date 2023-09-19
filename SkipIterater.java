
import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> skipMap;
    Integer nextEle;
    Iterator<Integer> nIt;

    public SkipIterator(Iterator<Integer> it) {
        this.skipMap = new HashMap<>();
        this.nextEle = null;
        this.nIt = it;
        advance();
    }

    private void advance() {
        nextEle = null;
        while (nIt.hasNext() && nextEle == null) {
            Integer el = nIt.next();
            if (skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);
            } else {
                nextEle = el;
            }
        }
    }

    public boolean hasNext() {
        return nextEle != null;
    }

    public Integer next() {
        Integer result = nextEle;
        advance();
        return result;
    }

    public void skip(int val) {
        if (val == nextEle) {
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }

}