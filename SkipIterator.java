import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> count;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.count = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        if (!hasNext())
            throw new RuntimeException("empty");
        Integer el = nextEl;
        advance();
        return el;
    }

    public void skip(int num) {
        if (!hasNext())
            throw new RuntimeException("empty");
        if (nextEl == num) {
            advance();
        } else {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }
    }

    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (!count.containsKey(el)) {
                nextEl = el;
            } else {
                count.put(el, count.get(el) - 1);
                count.remove(el, 0);
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> inputList = new ArrayList<>(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10));
        Iterator<Integer> inputIterator = inputList.iterator();
        SkipIterator skipIterator = new SkipIterator(inputIterator);
        System.out.println("Original list:");
        while (skipIterator.hasNext()) {
            System.out.print(skipIterator.next() + " ");
        }
        System.out.println();
        // Skip the element 5
        skipIterator.skip(5);
        System.out.println("List after skipping 5:");
        while (skipIterator.hasNext()) {
            System.out.print(skipIterator.next() + " ");
        }
        System.out.println();
        // Skip the element 2
        skipIterator.skip(2);
        System.out.println("List after skipping 2:");
        while (skipIterator.hasNext()) {
            System.out.print(skipIterator.next() + " ");
        }
        System.out.println();
    }
}