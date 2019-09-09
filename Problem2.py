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
		# Using one extra variable to store the peeked value
		self.iterator = iterator
		self.cache = None

	def peek(self):
		"""
		Returns the next element in the iteration without advancing the iterator.
		:rtype: int
		"""
		# We have to get the next value from the iterator only of the self.cache is none
		# None means that we have already taken out one element which is the first element
		if self.cache == None and self.iterator.hasNext():
			self.cache = self.iterator.next()
		return self.cache

	def next(self):
		"""
		:rtype: int
		"""
		res = None
		if self.cache != None:
			res = self.cache
			self.cache = None
		else:
			if self.iterator.hasNext():
				res = self.iterator.next() # get the next value from the iterator only if we have not called peek before that
		return res

	def hasNext(self):
		"""
		:rtype: bool
		"""
		if self.cache:
			return True # if there is a value in self.cache that mean that is the next value so we are returning true
		else:
			return self.iterator.hasNext()

# Your PeekingIterator object will be instantiated and called as such:
# iter = PeekingIterator(Iterator(nums))
# while iter.hasNext():
#     val = iter.peek()   # Get the next element but not advance the iterator.
#     iter.next()         # Should return the same value as [val].