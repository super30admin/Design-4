//Time Complexity:O(1)
//SPace Complexity:O(N) --queue
//In this problem, an iterator will be initialized. The initial value of peek will be -1. In the constructor, I'll be initialising the value of peek as the next value, only if the hasnext function, returns true. In the peek function, I'll be returning the value of peek. In the next function, I'll be returning the peek value and update the peek value to point to the next element.The hasnext function will return true, if peek is not equal to -1. If the type is changed from Integer to Template, then we can use all of these functions for any data type.
//This code was executed successfully and got accepted in leetcode. 
class PeekingIterator implements Iterator<Integer> {
    int peek=-1;
    Iterator<Integer> itr;
	public PeekingIterator(Iterator<Integer> iterator) {
	    // initialize any member here.
	    itr=iterator;
        if(itr.hasNext()){
            peek=itr.next();
        }
	}

    // Returns the next element in the iteration without advancing the iterator.
	public Integer peek() {
        return peek;
	}

	// hasNext() and next() should behave the same as in the Iterator interface.
	// Override them if needed.
	@Override
	public Integer next() {
	    int next= peek;
        peek= itr.hasNext()? itr.next():-1;
        return next;
	}

	@Override
	public boolean hasNext() {
	    return peek!=-1;
	}
}