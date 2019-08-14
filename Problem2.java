// Time Complexity :O(1) --> peek from queue
// Space Complexity :O(n) --> queue , n --> number of elements in given list.
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach
// 1. Initialise a queue of Integers and all elements of given iterator into queue by iterating over the iterator
// using iterator.hasNext() and itertor.next() functions.
// 2. For next() function poll element from queue and return it.
// 3. For hasNext() function check if queue is empty.
// 4. For peek() function , peek element from queue and return it.
class PeekingIterator implements Iterator<Integer> {
    Queue<Integer> queue;
	public PeekingIterator(Iterator<Integer> iterator) {
	    // initialize any member here.
	    queue = new LinkedList<>();
        while(iterator.hasNext())
        {
            queue.add(iterator.next());
        }
	}

    // Returns the next element in the iteration without advancing the iterator.
	public Integer peek() {
        if(queue.isEmpty()) return null;
        return queue.peek();
	}

	// hasNext() and next() should behave the same as in the Iterator interface.
	// Override them if needed.
	@Override
	public Integer next() {
        if(this.hasNext())
	        return queue.poll();
        return null;
	}

	@Override
	public boolean hasNext() {
	    if(queue.isEmpty()) return false;
        return true;
	}
}
