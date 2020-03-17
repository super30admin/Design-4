import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class SkipIterator implements Iterator<Integer> {

    private final Iterator<Integer> iterator;
    private final Map<Integer, Integer> skipCount;
    private Integer cursor;

    public SkipIterator(Iterator<Integer> iterator) {
        this.skipCount = new HashMap<>();
        this.iterator = iterator;
        seekCursor();
    }

    public boolean hasNext() {
        return cursor != null;
    }

    public Integer next() {
        Integer element = cursor;
        seekCursor();
        return element;
    }

    public void skip(int num) {
        if (cursor == num) {
            seekCursor();
        } else {
            skipCount.put(num, skipCount.getOrDefault(num, 0) + 1);
        }
    }

    private void seekCursor() {
        cursor = null;

        while (cursor == null && iterator.hasNext()) {
            Integer check = iterator.next();
            if (skipCount.containsKey(check)) {
                skipCount.put(check, skipCount.get(check) - 1);
                skipCount.remove(check, 0);
            } else {
                cursor = check;
            }

        }
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped. This method can be called multiple times in a row.
     * skip(5), skip(5) means that the next two 5s should be skipped.
     */

    public static void main(String[] args) {
        List<Integer> l = Arrays.asList(2, 3, 5, 2, 1, 7, 5, -1, 5, 10, 1);
        Iterator itr = l.iterator();
        SkipIterator skItr = new SkipIterator(itr);
        System.out.println(skItr.hasNext());
        System.out.println(skItr.next());
        skItr.skip(5);
        System.out.println(skItr.next());
        System.out.println(skItr.next());
        System.out.println(skItr.next());
        skItr.skip(5);
        skItr.skip(5);
        System.out.println(skItr.next());
        System.out.println(skItr.next());
        System.out.println(skItr.next());
        System.out.println(skItr.next());
        System.out.println(skItr.next());

    }
}