//idea is to use queue and offer all elements inside queue .to peek use peek method of queue. to check next() use queue.poll()
//to check hasNext() use q.isEmpty()

//sc:0(n) queue stores  elements
//tc:0(1) to insert element o(1) to pee and check next() so PeekingIterator take 0(1)
//Follow up: How would you extend your design to be generic and work with all types, not just integer? use Genric in java
//DID NOT UNDERSTAND YET HOW TO SOLVE FOLLOW UP QUESTION
//PROBLEM FACED WHILE SOLVING FOLLOWUP SOLUTION:RUN TIME EXCEPTION INTEGRER NOT YET IMPLEMENTED

package Design4;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

// Java Iterator interface reference:
	// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
	class PeekingIterator implements Iterator<Integer> {
	    
	    Queue<Integer> q = new LinkedList<Integer>();

		public PeekingIterator(Iterator<Integer> iterator) {
		    // initialize any member here.
	        while(iterator.hasNext()) {
	            q.offer(iterator.next());
	        }
		    
		}

	    // Returns the next element in the iteration without advancing the iterator.
		public Integer peek() {
	        return q.peek();
	        
		}

		// hasNext() and next() should behave the same as in the Iterator interface.
		// Override them if needed.
		@Override
		public Integer next() {
		    return q.poll();
		}

		@Override
		public boolean hasNext() {
		    return !q.isEmpty();
		}
	}

	
	//USING GENERIC TYPE
	/*import java.util.List;
	import java.util.NoSuchElementException;
	class PeekingIterator<E> implements Iterator<E> {
	    E next;
	    Iterator<E> iterator;
	    boolean isEnd;

	    public PeekingIterator(Iterator<E> iterator) {
	        this.iterator = iterator;
	        advance();
	    }

	    public E peek() {
	        if (isEnd)
	            throw new NoSuchElementException();
	        return next;
	    }

	    // isEnd() and next() should behave the same as in the Iterator interface.
	    // Override them if needed.
	    @Override
	    public E next() {
	        if (isEnd)
	            throw new NoSuchElementException();
	        E res = next;
	        advance();
	        return res;
	    }

	    @Override
	    public boolean hasNext() {
	        return !isEnd;
	    }

	    private void advance() {
	        if (iterator.hasNext()) {
	            next = iterator.next();
	        } else {
	            isEnd = true;
	        }
	    }*/

