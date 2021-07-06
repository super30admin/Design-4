# Time Complexity : O(n) for all, O(1) hasNext
# Space Complexity : O(n), where n is the length of the input list.
# Did this code successfully run on Leetcode : N.A.
# Any problem you faced while coding this : no

# Your code here along with comments explaining your approach

# A pointer is maintained to keep track of the next element to return
# and a hashmap to keep track of what number to skip and how many times.
from collections import defaultdict
class skipIterator(object):
	def __init__(self, it):
		self.nextEl = None
		self.toSkip = defaultdict(int)
		self.it = it
		self.advance()

	def hasNext(self):
		return self.nextEl != None

	def next(self):
		temp = self.nextEl
		self.advance()
		return temp

	def skip(self, val):
		if val == self.nextEl:
			self.advance()
		else:
			self.toSkip[val] += 1

	def advance(self):
		# find new nextEl
		self.nextEl = None
		temp = next(self.it, None)
		while not self.nextEl and temp:
			if self.toSkip[temp] > 0:
				self.toSkip[temp] -= 1
			else:
				self.nextEl = temp
				break
			temp = next(self.it, None)


# Example:
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
