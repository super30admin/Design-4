'''
Skip Iterator
https://leetcode.com/playground/Vn6kCbsZ

Design a Skip Iterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators (https://www.geeksforgeeks.org/java/how-to-use-iterator-in-java/) check similar problems (https://leetcode.com/problemset/?search=iterator).

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

Solution
1. Hash map
This SkipIterator lets you skip values dynamically while iterating.
We use a skipMap to track which numbers to skip and how many times.
The advance() method always sets nextEl to the next valid unskipped number.

https://youtu.be/iWTeSZoly9g?t=2285 (problem definition)
https://youtu.be/iWTeSZoly9g?t=2592 (discuss skip operation)
https://youtu.be/iWTeSZoly9g?t=3777 (interesting case: we are asked to skip the element we are pointing to. That is, next ele points to 4 but we are asked to skip 4!)
https://youtu.be/iWTeSZoly9g?t=3150 (coding)

                Time        Space
__init__()      O(1)        O(1)
hasNext()       O(1)        O(1)
next()          O(N)        O(1)
advance()       O(N)        O(1)
skip            O(N)        O(N)
(space for skip is O(N) if we skip all N elements and hence save in hash map)
'''
from collections import defaultdict

class SkipIterator:
    def __init__(self, it):
        ''' Time: O(1), Space: O(1) '''
        self.it  = it # default Python integer iterator
        self.nextEl = None
        self.skipMap = defaultdict(int)
        self.advance() # O(1) at init

    def hasNext(self):
        ''' Time: O(1), Space: O(1) '''
        return self.nextEl is not None

    def next(self):
        ''' Time: O(N), Space: O(1) '''
        val = self.nextEl
        self.advance()
        return val

    def advance(self):
        ''' Time: O(N), Space: O(1) '''
        try:
            self.nextEl = next(self.it)
        except:
            self.nextEl = None
            return

        while self.skipMap[self.nextEl] > 0:
            self.skipMap[self.nextEl] -= 1
            try:
                self.nextEl = next(self.it)
            except:
                self.nextEl = None
                return

    def skip(self, val):
        ''' Time: O(N), Space: O(N) '''
        if val == self.nextEl:
            self.advance() # Time: O(N)
        else:
            self.skipMap[val] += 1  # Space: O(N)


def run_SkipIterator():
    tests = [( ["SkipIterator", "skip", "skip", "skip", "hasNext", "next", "hasNext", "next", "hasNext", "next"],
               [[2,4,5,6,5,7,8,9], 6, 5, 5, None, None, None, None, None, None],
               [None,None,None,None,True,2,True,4,True,7]
             ),

             # Interesting: next (output: 2, curr moves to 4), skip 4, skip 5,
             # skip 5, next.
             # curr = 4 when we call next a 2nd time. But we cannot output 4
             # because skip() was called earlier to skip outputting 4,5,5.
             ( ["SkipIterator", "hasNext", "next", "skip", "skip", "skip", "hasNext", "next", "skip", "hasNext", "next", "hasNext", "next"],
               [[2,4,5,6,5,7,8,9],None,None,4,5,5,None,None,8,None,None,None,None],
               [None,True,2,None,None,None,True,6,None,True,7,True,9],
             ),

             ( ["SkipIterator", "hasNext", "next", "skip", "next", "next", "next", "skip", "skip", "next", "next", "next", "hasNext", "next"],
               [[2,3,5,6,5,7,5,-1,5,10], None, None, 5, None, None, None, 5,5,None, None, None, None, None],
               [None,True,2,None,3,6,5,None,None,7,-1,10,False,None]
             ),
    ]
    for test in tests:
        operations, inputs, ans = test[0], test[1], test[2]
        outputs = []
        print(f"\n------------")
        for operation, input in zip(operations, inputs):
            if operation == "SkipIterator":
                it = iter(input)
                sol = SkipIterator(it)
                output = None
            elif operation == "skip":
                output = sol.skip(input)
            elif operation == "hasNext":
                output = sol.hasNext()
            elif operation == "next":
                output = sol.next()
            print(f"{operation}({input}): {output}")
            outputs.append(output)
        print(f"\nOperations = {operations}")
        print(f"inputs = {inputs}")
        print(f"outputs = {outputs}")
        success = (ans==outputs)
        print(f"Pass: {success}")
        if not success:
            print("Failed")
            return

run_SkipIterator()
