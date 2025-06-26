"""
problem: SkipIterator
Approach: Use a basic iterator to traverse and have O(1) operations for next and has next while storing the elements to skip
in a hashmap

"""
from collections import defaultdict
class SkipIterator:
    def __init__(self, itr):
        self.itr = itr
        self.skipMap = defaultdict(int)
        self.currElement = None
        self.advance()
        
    def hasNext(self):
        if self.currElement:
            return True
        return False
    
    def next(self):
        ret = self.currElement
        self.advance()
        return ret
        
    
    def skip(self, val):
        if self.currElement and  self.currElement == val:
            if val in self.skipMap:
                self.skipMap[val] -= 1
                if self.skipMap[val] == 0:
                    del self.skipMap[val]
        self.skipMap[val] += 1
        
    def advance(self):
        curr = None
        while not curr:
            nxt = next(self.itr, None)
            if not nxt:
                break
            if nxt in self.skipMap:
                self.skipMap[nxt] -= 1
                if self.skipMap[nxt] == 0:
                    del self.skipMap[nxt]
            else:
                curr = nxt
        self.currElement = curr
        return True if curr else False
    
if __name__ == '__main__':
    skipItr = SkipIterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))
    print(skipItr.hasNext()) # true

    print(skipItr.next()) # returns 2

    skipItr.skip(5)

    print(skipItr.next()) # returns 3

    print(skipItr.next()) # returns 6 because 5 should be skipped

    print(skipItr.next()) # returns 5

    skipItr.skip(5)

    skipItr.skip(5)

    print(skipItr.next()) # returns 7

    print(skipItr.next()) # returns -1

    print(skipItr.next()) # returns 10

    print(skipItr.hasNext()) # false

    print(skipItr.next()) #error 
