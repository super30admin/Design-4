import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> nit; // Original iterator
    private HashMap<Integer, Integer> freq; // HashMap to store the frequency of elements to be skipped
    private Integer nextElement; // The next element to be returned

    // Constructor to initialize the iterator and HashMap, and find the first valid element
    public SkipIterator(Iterator<Integer> nit) {
        this.nit = nit;
        this.freq = new HashMap<>();
        nextCorrectPos(); // Find the first valid element
    }

    // Method to find the next valid element
    private void nextCorrectPos() {
        nextElement = null; // Reset nextElement to null at the start
        while (nextElement == null && nit.hasNext()) { // Iterate until a valid nextElement is found or no more elements
            Integer el = nit.next();
            if (freq.containsKey(el)) { // If the element should be skipped
                freq.put(el, freq.get(el) - 1); // Decrease its count in the HashMap
                if (freq.get(el) == 0) { // If the count reaches zero, remove the element from the HashMap
                    freq.remove(el);
                }
            } else {
                nextElement = el; // Set the element as the next valid element
            }
        }
    }

    // Check if there is a next valid element
    @Override
    public boolean hasNext() {
        return nextElement != null;
    }

    // Return the next valid element
    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException(); // Throw exception if no next element
        }
        Integer el = nextElement;
        nextCorrectPos(); // Find the next valid element
        return el;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */ 
    public void skip(int val) {
        if (nextElement != null && nextElement.equals(val)) { // If the current nextElement should be skipped
            nextCorrectPos(); // Find the next valid element
        }
        freq.put(val, freq.getOrDefault(val, 0) + 1); // Add the element to the HashMap or increase its count
    }

    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());

        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5); // Skip the next 5
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because the next 5 should be skipped
        System.out.println(itr.next()); // returns 7
        itr.skip(5); // Skip the next 5
        itr.skip(5); // Skip another 5
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
    }
}
