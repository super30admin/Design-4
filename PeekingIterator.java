// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
/*
The time complexity of peek(), next() and hasNext() is O(1)

Here the idea is to main the value when peek is called. When peek is called we call the next() method but maintain the value in cache
So we can return it when again peek or next is called.

Yes, the solution passed all the test cases in leetcode.

*/
class PeekingIterator implements Iterator<Integer> {

    Integer cache;
    Iterator<Integer> iterator;

    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        this.cache = null;
        this.iterator = iterator;

    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        if(cache==null){
            if(iterator.hasNext()){
                cache = iterator.next();
                return cache;
            }
        }

        return cache;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {

        Integer old = cache;
        if(cache!=null){
            cache = null;
            return old;
        }
        else if(iterator.hasNext()){
            return iterator.next();
        }
        return null;
    }

    @Override
    public boolean hasNext() {

        if(cache!=null){
            return true;
        }
        else{
            return iterator.hasNext();
        }
    }
}