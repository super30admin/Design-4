import itertools
# Below is the interface for Iterator, which is already defined for you.
#
class Iterator:
    def __init__(self, nums):
        """
        Initializes an iterator object to the beginning of a list.
        :type nums: List[int]
        """
        self.iter = nums[0]
        self.nums = nums
        self.idx = 0

    def hasNext(self):
        """
        Returns true if the iteration has more elements.
        :rtype: bool
        """
        if self.idx < len(self.nums):
            return True
        return False        

    def next(self):
        """
        Returns the next element in the iteration.
        :rtype: int
        """
        if self.idx < len(self.nums):
            self.idx = self.idx + 1
            return self.nums[self.idx - 1]
        return -1   

class SkipIterator:
    def __init__(self, iterator):
        """
        Initialize your data structure here.
        :type iterator: Iterator
        """
        self.map = iterator
        self.skipelem = []
        
    def skip(self, val):
        """
        Returns the next element in the iteration without advancing the iterator.
        :rtype: int
        """
        self.skipelem.append(val)
        # return self.peekval    

    def next(self):
        """
        :rtype: int
        """
        if self.map.hasNext():
            val = self.map.next()
            if val in self.skipelem:
                self.skipelem.pop(self.skipelem.index(val))
                val = self.map.next()
            return val
        

    def hasNext(self):
        """
        :rtype: bool
        """
        return self.map.hasNext()
        

# Your SkipIterator object will be instantiated and called as such:
nums = [2, 3, 5, 6, 5, 7, 5, -1, 5, 10]
itr = SkipIterator(Iterator(nums))
print(itr.hasNext()) # true
print(itr.next()) # returns 2
itr.skip(5)
print(itr.next()) # returns 3
print(itr.next()) # returns 6 because 5 should be skipped
print(itr.next()) # returns 5
itr.skip(5)
itr.skip(5)
print(itr.next()) # returns 7
print(itr.next()) # returns -1
print(itr.next()) # returns 10
print(itr.hasNext()) # false
print(itr.next()) # error       # Should return the same value as [val].