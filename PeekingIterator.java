/**
 * Given an Iterator class interface with methods: next() and hasNext(), design
 * and implement a PeekingIterator that support the peek() operation -- it
 * essentially peek() at the element that will be returned by the next call to
 * next().
 * 
 * Idea: Cache the next element, rest is simple to follow.
 * 
 * Leetcode Attempts: 1
 * 
 * Leetcode Results:
 * Runtime: 46 ms, faster than 70.42% of Java online submissions for Peeking Iterator.
 * Memory Usage: 35.7 MB, less than 100.00% of Java online submissions for Peeking Iterator.
 */
// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
class PeekingIterator implements Iterator<Integer> {

    Integer cache;
    Iterator<Integer> iterator;

    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        this.iterator = iterator;

        if (iterator.hasNext()) {
            cache = iterator.next();
        }
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return cache;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        Integer result = cache;
        cache = null;
        if (iterator.hasNext()) {
            cache = iterator.next();
        }
        return result;
    }

    @Override
    public boolean hasNext() {
        return cache != null;
    }
}