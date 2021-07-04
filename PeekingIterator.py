'''
Problems faced: How to peek without increment the iterator
Did it run on leet code: Yes

Solution:
- Created a extra variable 'current' which stores next value
- and incremented iterator based on the conditions
'''



# Below is the interface for Iterator, which is already defined for you.
#
# class Iterator(object):
#     def __init__(self, nums):
#         """
#         Initializes an iterator object to the beginning of a list.
#         :type nums: List[int]
#         """
#
#     def hasNext(self):
#         """
#         Returns true if the iteration has more elements.
#         :rtype: bool
#         """
#
#     def next(self):
#         """
#         Returns the next element in the iteration.
#         :rtype: int
#         """

class PeekingIterator(object):
    def __init__(self, iterator):
        """
        Initialize your data structure here.
        :type iterator: Iterator
        """
        self.iterator = iterator
        self.current = self.iterator.next() if self.iterator.hasNext() else None
            
        

    def peek(self):
        """
        Returns the next element in the iteration without advancing the iterator.
        :rtype: int
        """
        return self.current
        
    def next(self):
        """
        :rtype: int
        """
        ans = self.current
        self.current = self.iterator.next() if self.iterator.hasNext() else None
        return ans
            
    def hasNext(self):
        """
        :rtype: bool
        """
        return not self.current==None
        

# Your PeekingIterator object will be instantiated and called as such:
# iter = PeekingIterator(Iterator(nums))
# while iter.hasNext():
#     val = iter.peek()   # Get the next element but not advance the iterator.
#     iter.next()         # Should return the same value as [val].