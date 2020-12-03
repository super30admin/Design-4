# Design-4

## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)




## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.

class SkipIterator implements Iterator<Integer> {

	public SkipIterator(Iterator<Integer> it) {
	}

	public boolean hasNext() {
	}

	public Integer next() {
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
	}
}
Example:

SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
itr.hasNext(); // true
itr.next(); // returns 2
itr.skip(5);
itr.next(); // returns 3
itr.next(); // returns 6 because 5 should be skipped
itr.next(); // returns 5
itr.skip(5);
itr.skip(5);
itr.next(); // returns 7
itr.next(); // returns -1
itr.next(); // returns 10
itr.hasNext(); // false
itr.next(); // error

