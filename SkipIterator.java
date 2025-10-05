import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//TC: Amortized O(1) for next, skip, perfect O(1) for hasNext
//SC: O(k) where k is the number of skipped elements
public class SkipIterator implements Iterator<Integer> {

    private final Iterator<Integer> iterator;
    private Integer nextEl;
    private final Map<Integer, Integer> frequency;

    public SkipIterator(Iterator<Integer> iterator) {
        this.frequency = new HashMap<>();
        this.iterator = iterator;
        advance();
    }


    @Override
    public boolean hasNext() {
        return this.nextEl != null;
    }

    @Override
    public Integer next() {
        Integer temp = nextEl;
        advance();
        return temp;
    }

    public void skip(int num) {
        if (num == nextEl) advance();
        else this.frequency.put(num, this.frequency.getOrDefault(num, 0) + 1);
    }

    private void advance() {
        this.nextEl = null;
        while (this.iterator.hasNext() && this.nextEl == null) {
            Integer current = this.iterator.next();
            if (this.frequency.containsKey(current)) {
                this.frequency.put(current, this.frequency.get(current) - 1);
                this.frequency.remove(current, 0);
            } else {
                this.nextEl = current;
            }
        }
    }
}
