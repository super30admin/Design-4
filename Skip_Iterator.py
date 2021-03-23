# Created by Aashish Adhikari at 11:51 AM 3/23/2021
'''
Design a SkipIterator that supports a method skip(int val).
When it is called the next element equals val in iterator sequence should be skipped.
If you are not familiar with Iterators check similar problems.



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

Your SKipIterator will be called as follows:

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

'''

'''
Time Complexity:
has_next(): O(n) since in the worst case we could have n repetitions of an integer that needs to be skipped. In this case, we skip all n repetitions using the while loop.
next(): O(1)
skip(): O(1)


Space Complexity:
has_next(): O(1)
next(): O(1)
skip(): O(1)

'''

class SkipIterator():

    def __init__(self, iterator):


        self.iterator = iterator
        self.hm_skip_count = {}
        self.stack = []


    def has_next(self):
        '''
        makes sure there is an integer on top of the stack if a valid integer available
        '''

        if len(self.stack) == 0:
            next_elem = next(self.iterator)

            # find a valid next element
            while next_elem in self.hm_skip_count and self.hm_skip_count[next_elem] > 0:
                self.hm_skip_count[next_elem] -= 1
                next_elem = next(self.iterator)
            # Now we have either an element from the native iterator that does not require to be skipped or
            # None because the native iterator was exhausted

            # if we have a valid next_element, put it on top of the stack and return True else return False
            if next_elem is not None:
                # we have a valid element
                self.stack.append(next_elem)
                return True
            else:
                # we dont have a valid element
                return False
        else:
            # we already have a valid element on top of the stack to return
            return True

    def next(self):
        if self.has_next():
            return self.stack.pop()
        else:
            return


    def skip(self, num):
        if num not in self.hm_skip_count:
            self.hm_skip_count[num] = 1
        else:
            self.hm_skip_count[num] += 1


itr = SkipIterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))
print(itr.has_next()) #// true
print(itr.next()) #// returns 2
itr.skip(5)
print(itr.next()) #// returns 3
print(itr.next()) #// returns 6 because 5 should be skipped
print(itr.next()) #// returns 5
itr.skip(5)
itr.skip(5)
print(itr.next()) #// returns 7
print(itr.next()) #// returns -1
print(itr.next()) #// returns 10
print(itr.has_next()) #// false
print(itr.next()) #// error