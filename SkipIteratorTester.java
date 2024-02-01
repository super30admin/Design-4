/*
* Approach:
*  1. Maintain hashmap to keep track of skip elements
        next element to store the element that need to be sent next
        Globlal iterator to iterate on elements
* 
*  2. advance(): function which assigns next elements to nextElement,
         based on skip elements stored in hashmap.
* 
*  3. skip(num) : if num == nextElement, get the next valid element using advance()
        else store in hashmap for future check
* 
* 
* Did this code successfully run on Leetcode : YES
* 
* Any problem you faced while coding this : NO
* 
* Time Complexity: 
    hasNext() - O(1)
    next() - O(N)
* 
* Space Complexity: O(1)
* 
*/

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {
    Integer nextElement;

    HashMap<Integer, Integer> hmap;

    Iterator<Integer> it;

    public SkipIterator(Iterator<Integer> it) {
        this.hmap = new HashMap<>();

        this.it = it;

        this.nextElement = null;

        advance();
    }

    public boolean hasNext() {
        return nextElement != null;
    }

    private void advance() {
        nextElement = null;

        while (it.hasNext()) {
            Integer element = it.next();

            // System.out.println("element in advance:"+ element);

            if (!hmap.containsKey(element)) {
                nextElement = element;
                // System.out.println("next element in advance:"+ nextElement);
                break;
            }

            hmap.put(element, hmap.get(element) - 1);

            if (hmap.get(element) == 0) {
                hmap.remove(element);
            }
        }
    }

    public Integer next() {
        Integer element = nextElement;

        advance();

        return element;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public void skip(int val) {
        // System.out.println("value:"+ val);
        // System.out.println("next element:"+ nextElement);

        if (nextElement == val) {
            advance();
        }

        else {
            hmap.put(val, hmap.getOrDefault(val, 0) + 1);
        }
    }
}

public class SkipIteratorTester {
    public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8).iterator());

        System.out.println(it.hasNext());// true // nextEl = 5
        it.skip(5);// nextEl = 6
        System.out.println();
        System.out.println(it.next()); // 6 nextEl = 7
        System.out.println();
        it.skip(5);
        System.out.println();
        System.out.println(it.next());// 7 nextEl = 6
        System.out.println();
        System.out.println();
        System.out.println(it.next()); // 6nextEl = 6
        System.out.println();
        it.skip(8); //
        it.skip(9); //
        System.out.println();
        System.out.println(it.next()); // 5
        System.out.println();
        System.out.println();
        System.out.println(it.next()); // 5
        System.out.println();
        System.out.println();
        System.out.println(it.next());// 6
        System.out.println();

        System.out.println(it.hasNext());// true
        // it.skip(8);
        // System.out.println(it.hasNext()); //false
        System.out.println();
        System.out.println(it.next());// 8
        System.out.println();
        // it.skip(1);

        // it.skip(3);

        // System.out.println(it.hasNext()); //false

    }
}