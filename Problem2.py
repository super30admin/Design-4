# Time Complexity : O(1)
# Space Complexity : O(1)
# Did this code successfully run on Leetcode : Yes
# Three line explanation of solution in plain english:
# - Keep one variable as cache to store value of next element.
# - At initialization If we have next item, Update cache by calling getNext.
# - For peek function just return the cached value. For hasNext function check if cache has value or not because it is storing next value.
# - For next function return the cache as answer and update the cache by next eleemnt.

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
#       If iterator has any next element, store that element in the cache variable
        if self.iterator.hasNext():
            self.cache = iterator.next()
#       Othervise store None as cache variable
        else:
            self.cache = None
        

    def peek(self):
        """
        Returns the next element in the iteration without advancing the iterator.
        :rtype: int
        """
#       Return cache variable in return of eek function
        return self.cache     

    def next(self):
        """
        :rtype: int
        """
#       Store current cache variable in temp variable that we will return in the end, because value of cache is overwritten by updateint it.
        ans = self.cache
#       Update cache variable if possible
        if self.iterator.hasNext():
            self.cache = self.iterator.next()
        else:
            self.cache = None
#       Return temp variable that was storing previous cache.
        return ans
        

    def hasNext(self):
        """
        :rtype: bool
        """
#       Check if cache is None or not and return it as answer. Because cache has next value.
        return self.cache != None
        

# Your PeekingIterator object will be instantiated and called as such:
# iter = PeekingIterator(Iterator(nums))
# while iter.hasNext():
#     val = iter.peek()   # Get the next element but not advance the iterator.
#     iter.next()         # Should return the same value as [val].
