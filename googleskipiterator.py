# Time Complexity : O(1) amortized for next and hasNext, O(1) for skip
# Space Complexity : O(m) where m is the number of unique elements to be skipped
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Your code here along with comments explaining your approach:
# I am using a hashmap to store the elements to be skipped and their counts.
# The advance method is used to move the iterator to the next valid element that is not skipped and update the nextEl variable.
# The next method returns the current element and advances the iterator.


class ListIterator:
    def __init__(self, lst):
        self.lst = lst
        self.index = 0
    
    def has_next(self):
        return self.index < len(self.lst)
    
    def next(self):
        if self.has_next():
            val = self.lst[self.index]
            self.index += 1
            return val
        return None

class SkipIterator:
    
    def __init__(self, it):
        # If it's a list, convert to iterator
        if isinstance(it, list):
            self.it = ListIterator(it)
        else:
            self.it = it
        self.skipMap = {}
        self.nextEl = None
        self.advance()
        
    def advance(self):
        self.nextEl = None
        while self.it.has_next() and self.nextEl is None:
            el = self.it.next()
            if el in self.skipMap:
                self.skipMap[el] -= 1
                if self.skipMap[el] == 0:
                    del self.skipMap[el]
            else:
                self.nextEl = el
                return

    def has_next(self):
        return self.nextEl is not None
        
    def next(self):
        temp = self.nextEl
        self.advance()
        return temp
    
    def skip(self, val):
        if self.nextEl == val:
            self.advance()
        else:
            self.skipMap[val] = self.skipMap.get(val,0) + 1

# Fixed method calls
itr = SkipIterator([5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8])
print(itr.has_next())  # True
itr.skip(5)
print(itr.next())     # 6
itr.skip(5)
print(itr.next())     # 7
print(itr.next())     # 6
itr.skip(8)
itr.skip(9)
print(itr.next())     # 5
print(itr.next())     # 5
print(itr.next())     # 6
print(itr.has_next())  # True
print(itr.next())     # 8
print(itr.has_next())  # False