// Problem 2 - Skip Iterator
// Time Complexity: O(1)
// Space Complexity: O(1)

class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private final Map<Integer, Integer> map;
    private Integer curr;

    public SkipIterator(Iterator<Integer> it) {
        this.iterator = it;
        this.map = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return curr != null;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            return null;
        }
        Integer temp = curr;
        advance();
        return temp;
    }

    public void skip(int num) {
        if (!hasNext()) {
            return;
        }
        
        if (curr == num) {
            advance();
        } else {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
    }

    private void advance() {
        curr = null;

        while (curr == null && iterator.hasNext()) {
            Integer el = iterator.next();
            if (!map.containsKey(el)) {
             curr = el;
            } else {
             map.put(el, map.get(el) - 1);
             map.remove(el, 0);
            }
        }

    }
}

public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        System.out.println(it.next());
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }
}
