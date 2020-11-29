"""
Time Complexity : O(n) 
Space Complexity : O(n) 
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : No


Your code here along with comments explaining your approach
We maintain a map for storing values that need to be skipped but if we are on a value that needs to be skipped,
we just do .next.
"""
from collections import defaultdict


class skipIterator(object):
    def __init__(self, it):
        self.it = it
        self.nextElement = None
        self.skipMap = defaultdict(int)
        self.advance()

    def next(self):
        temp = self.nextElement
        self.advance()
        return temp

    def hasNext(self):
        return self.nextElement != None

    def skip(self, val):
        if self.nextElement == val:
            self.advance()
        else:
            self.skipMap[val] += 1

    def advance(self):
        self.nextElement = None
        tempNext = next(self.it, None)
        while not self.nextElement and tempNext:
            if self.skipMap[tempNext] > 0:
                self.skipMap[tempNext] -= 1
            else:
                self.nextElement = tempNext
                break
            tempNext = next(self.it, None)


itr = skipIterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))
print(itr.hasNext())   # true
print(itr.next())      # returns 2
itr.skip(5)
print(itr.next())      # returns 3
print(itr.next())      # returns 6 because 5 should be skipped
print(itr.next())      # returns 5
itr.skip(5)
itr.skip(5)
print(itr.next())      # returns 7
print(itr.next())      # returns -1
print(itr.next())      # returns 10
print(itr.hasNext())   # returns false
print(itr.next())      # None
