# Time Complexity : O(n) where n is the length of the lisr
# Space Complexity : O(1)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No


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
        self.next_elem = []

    def peek(self):
        """
        Returns the next element in the iteration without advancing the iterator.
        :rtype: int
        """
        if not self.next_elem:
            self.next_elem.append(self.iterator.next())
        return self.next_elem[0]

    def next(self):
        """
        :rtype: int
        """
        if self.next_elem:
            return self.next_elem.pop(0)
        else:
            return self.iterator.next()

    def hasNext(self):
        """
        :rtype: bool
        """
        return len(self.next_elem) != 0 or self.iterator.hasNext()

# Your PeekingIterator object will be instantiated and called as such:
# iter = PeekingIterator(Iterator(nums))
# while iter.hasNext():
#     val = iter.peek()   # Get the next element but not advance the iterator.
#     iter.next()         # Should return the same value as [val].

# Here I am maintaining a list for the peek value. when a peek is executed, the next element is inserted into next_elem it if it
# is empty. When the next is called, if the next_elem is not elmpty then element is taken out from it else the next element is returned.
