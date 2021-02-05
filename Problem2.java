import java.util.HashMap;
import java.util.Iterator;

//Time complexity : O(1)
//Space complexity : O(N)
class SkipIterator implements Iterator {
    HashMap<Integer, Integer> skipFreq;
    Iterator<Integer> itr;
    int value;

    public SkipIterator(Iterator<Integer> it) {
        this.itr = it;
        skipFreq = new HashMap<>();
    }

    public boolean hasNext() {
        skipElement();
        return itr.hasNext();
    }

    public Integer next() {
        skipElement();
        return value;
    }

    public void skip(int val) {
        skipFreq.put(val, skipFreq.getOrDefault(val, 0) + 1);
    }

    public void skipElement() {
        if (itr.hasNext()) {
            value = itr.next();
            while (skipFreq.containsKey(value)) {
                int freq = skipFreq.get(value);
                freq = freq - 1;
                if (freq == 0) {
                    skipFreq.remove(value);
                } else {
                    skipFreq.put(value, freq);
                }
                if (itr.hasNext())
                    value = itr.next();
                else {
                    value = null;
                    return;
                }
            }
        } else {
            value = null;
        }
    }

}
