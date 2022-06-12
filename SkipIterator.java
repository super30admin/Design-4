import java.util.*;

public class SkipIterator implements Iterator<Integer> {
    Integer nxtEl; // next element of skip iterator
    HashMap<Integer, Integer> map;
    Iterator<Integer> it;
    public SkipIterator(Iterator<Integer> it) {
        this.map = new HashMap<>();
        this.it = it;
        advance();
    }

    private void advance() {
        // to put the nxtEl of skip iterator at next valid position
        this.nxtEl = null;
        while(nxtEl == null && it.hasNext()) {
            Integer el = it.next();
            if(!map.containsKey(el)) {
                nxtEl = el;
            }
            else {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        Integer result = nextEl;
        advance();
        return result;
    }

    public void skip(int num) {
        if(num == nxtEl) {
            advance();
        }
        esle {
            map.put(num, map.getorDefault(num, 0) + 1);
        }
    }
}
