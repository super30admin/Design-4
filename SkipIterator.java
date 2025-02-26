/*
 * Time Complexity: hasNext() - O(1), next() - O(1), skip() - O(1)
 * Space Complexity: O(k) where n is the number of elements to be skipped
 */
import java.util.*;

public class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> iterator;

    // queue of skipped elements
    Queue<Integer> skippedElements;

    // stores nextElement from hasNext check
    Integer nextElement;

    public SkipIterator(Iterator<Integer> it) {
        this.iterator = it;
        skippedElements = new LinkedList<>();
        nextElement = null;
    }

    @Override
    public boolean hasNext() {
        // if there is a nextElement, we return true
        if(nextElement != null) {
            return true;
        }
        // if there are no skipped elements, simply return
        // iterator.hasNext()
        if(skippedElements.isEmpty()) {
            return iterator.hasNext();
        }
        if(iterator.hasNext()) {
            // nextElement stores the next element from the iterator
            // if it is equal to the top of the skippedElements, we remove it
            // and return the iterator.hasNext
            nextElement = iterator.next();
            if(nextElement.equals(skippedElements.peek())) {
                skippedElements.poll();
                nextElement = null;
                return this.hasNext();
            } else {
                // if not, we return true
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer next() {
        // if there is a nextElement, we set it to null
        // and return it
        if(nextElement != null) {
            Integer tmp = nextElement;
            nextElement = null;
            return tmp;
        }
        // if there are no skipped elements, we return the next element
        // from the iterator
        if(skippedElements.isEmpty()) {
            return iterator.next();
        }
        // if there are skipped elements, we check if the next element
        // is equal to the top of the skippedElements
        if(iterator.hasNext()) {
            // local nextElement (not class variable) stores the next element
            nextElement = iterator.next();
            // if it is equal to the first of the skippedElements, we remove it
            // and return iterator.next()
            if(nextElement.equals(skippedElements.peek())) {
                skippedElements.poll();
                nextElement = null;
                return this.next();
            } else {
                // else we return the value of the nextElement
                // and set it to null
                Integer tmp = nextElement;
                nextElement = null;
                return tmp;
            }
        }
        throw new NoSuchElementException("Iterator is empty.");
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        // just add the value to be skipped to the queue
        skippedElements.offer(val);
    }
}

class Driver {
    public static void main(String[] args) {
        List<Integer> example = Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10);
        SkipIterator itr = new SkipIterator(example.iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // error
    }
}
/* Output
true
2
3
6
true
5
true
7
-1
10
false
Exception in thread "main" java.util.NoSuchElementException
	at java.base/java.util.Arrays$ArrayItr.next(Arrays.java:4307)
	at SkipIterator.next(SkipIterator.java:62)
	at Driver.main(SkipIterator.java:114)
*/