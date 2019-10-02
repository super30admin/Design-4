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
'''

Approach  Use a cache variable to store the value to be returned by iterator.next()

Edge case : When we reach end of iterator set current = None

Time complexity next - O(1) peek - O(1)
Space complexity next - O(1) peek - O(1) // given iterator

Works on leet code
'''

class PeekingIterator(object):
    
    current = None
    iterator = None
    
    def __init__(self, iterator):
        """
        Initialize your data structure here.
        :type iterator: Iterator [1,2,3]
        """
        self.iterator = iterator
        
        self.current = self.iterator.next()  if iterator.hasNext() else None
        # current : 1 iterator [2,3]
    
    
    
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
        old  = self.current
        self.current = self.iterator.next() if self.iterator.hasNext() else None
        return old
    
    
    
    def hasNext(self):
        """
        :rtype: bool
        """
        return self.current != None

# Your PeekingIterator object will be instantiated and called as such:
# iter = PeekingIterator(Iterator(nums))
# while iter.hasNext():
#     val = iter.peek()   # Get the next element but not advance the iterator.
#     iter.next()         # Should return the same value as [val].