/* Approach : The skip method in the SkipIterator class marks an element to be skipped by incrementing its count in a map if it is not 
the current element; if it is the current element, it advances to the next element. The `advance` method moves the iterator to the next 
valid element by repeatedly fetching the next element from the underlying iterator and checking if it should be skipped (based on the map). 
if an element is to be skipped, it decrements its count in the map and continues until a non-skipped element is found or the iterator is 
exhausted. This ensures that the `next` method always returns the correct element.
 */
// Custom iterator that supports skipping elements
class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> it; // Original iterator
    private final Map<Integer, Integer> count; // Map to keep track of elements to skip
    private Integer nextEl; // Next element to return

    // Constructor
    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.count = new HashMap<>();
        advance(); // Initialize nextEl
    }

    // Check if there are more elements
    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    // Return the next element
    @Override
    public Integer next() {
        if (!hasNext()) throw new RuntimeException("empty");
        Integer el = nextEl;
        advance(); // Move to the next element
        return el;
    }

    // Skip the next occurrence of num
    public void skip(int num) {
        if (!hasNext()) throw new RuntimeException("empty");
        if (nextEl == num) {
            advance(); // Skip the current element
        } else {
            count.put(num, count.getOrDefault(num, 0) + 1); // Increment skip count for num
        }
    }

    // Advance to the next non-skipped element
    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (!count.containsKey(el)) {
                nextEl = el; // Found a non-skipped element
            } else {
                count.put(el, count.get(el) - 1); // Decrement skip count
                count.remove(el, 0); // Remove from map if count is 0
            }
        }
    }
}

// Main class to test SkipIterator
public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8).iterator());
        System.out.println(it.hasNext()); // true
        System.out.println(it.next()); // 5
        it.skip(5); // Skip next 5
        System.out.println(it.next()); // 6
        System.out.println(it.next()); // 7
        it.skip(7); // Skip next 7
        it.skip(9); // Skip next 9
        System.out.println(it.next()); // 6
        System.out.println(it.next()); // 8
        System.out.println(it.next()); // 5
        it.skip(8); // Skip next 8
        it.skip(5); // Skip next 5
        System.out.println(it.hasNext()); // true
        System.out.println(it.next()); // 6
        System.out.println(it.hasNext()); // false
    }
}
