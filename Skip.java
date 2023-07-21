import java.util.*;

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Integer nextEle;
    Iterator<Integer> nit;

    public SkipIterator(Iterator<Integer> it) {
        this.map = new HashMap<>();
        this.nit = it;
        advance();
    }

    private void advance() {
        this.nextEle = null;
        while (this.nit.hasNext() && this.nextEle == null) {
            Integer el = nit.next();
            if (!map.containsKey(el)) {
                this.nextEle = el;
            } else {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            }
        }
    }

    public boolean hasNext() {
        return this.nextEle != null;
    }

    public Integer next() {
        Integer re = this.nextEle;
        advance();
        return re;

    }

    public void skip(int val) {
        if (val == this.nextEle) {
            advance();
        } else {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
    }
}