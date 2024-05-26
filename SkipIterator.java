import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {

    private final Iterator<Integer> iterator;
    private final Map<Integer, Integer> skipped;
    private Integer value;

    public SkipIterator(Iterator<Integer> iterator) {
        this.iterator = iterator;
        this.skipped = new HashMap<>();
    }

    @Override
    public boolean hasNext() {
        if (value == null && !iterator.hasNext()) return false;
        if (value == null) value = iterator.next();
        removeSkipped();
        return value != null;
    }

    @Override
    public Integer next() {
        if (value == null) value = iterator.next();
        removeSkipped();
        int result = value;
        value = null;
        return result;
    }

    public void skip(int val) {
        skipped.put(val, skipped.getOrDefault(val, 0) + 1);
    }

    private void removeSkipped() {
        while (skipped.containsKey(value)) {
            skipped.put(value, skipped.get(value) - 1);
            if (skipped.get(value) == 0) {
                skipped.remove(value);
            }
            value = iterator.next();
        }
    }
}
