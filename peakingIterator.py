# Time Complexity: O(1)
# Space Complexity: O(1)
# Approach: Save the next element of the iterator on peaking and return that same element when next is called. Also clear the peking element.
# Below is the interface for Iterator, which is already defined for you.
#
# class Iterator:
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

class PeekingIterator:
    def __init__(self, iterator):
        """
        Initialize your data structure here.
        :type iterator: Iterator
        """
        self.iterator = iterator
        self.peeked_element = None
        

    def peek(self):
        """
        Returns the next element in the iteration without advancing the iterator.
        :rtype: int
        """
        if self.peeked_element is not None:
            return self.peeked_element
        
        if self.iterator.hasNext():
            self.peeked_element = self.iterator.next()
            return self.peeked_element
        return None
        

    def next(self):
        """
        :rtype: int
        """
        if self.peeked_element is not None:
            value = self.peeked_element
            self.peeked_element = None
            return value
        
        return self.iterator.next()
            

    def hasNext(self):
        """
        :rtype: bool
        """
        if self.peeked_element is not None:
            return True
        return self.iterator.hasNext()

# Your PeekingIterator object will be instantiated and called as such:
# iter = PeekingIterator(Iterator(nums))
# while iter.hasNext():
#     val = iter.peek()   # Get the next element but not advance the iterator.
#     iter.next()         # Should return the same value as [val].