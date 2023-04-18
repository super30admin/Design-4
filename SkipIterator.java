import java.util.*;

public class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> iterator;
    private Set<Integer> skipList;
    private Integer nextElement;

    public SkipIterator(Iterator<Integer> iterator) {
        this.iterator = iterator;
        this.skipList = new HashSet<>();
        findNext();
    }

    @Override
    public boolean hasNext() {
        return nextElement != null;
    }

    @Override
    public Integer next() {
        if (!hasNext()) throw new NoSuchElementException();
        Integer result = nextElement;
        findNext();
        return result;
    }

    public void skip(int val) {
        skipList.add(val);
        if (nextElement != null && nextElement.equals(val)) {
            findNext();
        }
    }

    private void findNext() {
        nextElement = null;
        while (nextElement == null && iterator.hasNext()) {
            Integer element = iterator.next();
            if (!skipList.contains(element)) {
                nextElement = element;
            }
        }
    }
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10));
        SkipIterator iterator = new SkipIterator(list.iterator());
        System.out.println(iterator.hasNext()); // true
        System.out.println(iterator.next()); // 2
        iterator.skip(5);
        System.out.println(iterator.next()); // 3
        System.out.println(iterator.next()); // 6
        System.out.println(iterator.next()); // 7
        iterator.skip(5);
        iterator.skip(5);
        System.out.println(iterator.next()); // -1
        System.out.println(iterator.next()); // 10
        System.out.println(iterator.hasNext()); // false
    }

}
