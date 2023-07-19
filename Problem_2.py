"""
Problem : 2

Time Complexity : O(n)
Space Complexity : O(n)

Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : No

Initializing the native iterator to the start of the input array
skip() - if a value needs to be skipped, increasing the counter of that key with that value in hashmap
hasnext() - checking if the value of the native iterator falls within the range of the length of array
next() - first checking if the next value exists, then checking if the value at the next place needs to be
skipped, if no, then incrementing the pointer and returning the value at next place, if Yes, decrementing the count
of the key with the value matching next number in the hashmap, if it becomes 0, which means it should not be skipped now,
deleting the key matching the value from hashmap, increasing the pointer by 1 and checking again if the next element should
be skipped or should be printed
"""

# Skip Iterator

from collections import defaultdict
class SkipIterator:

  def __init__(self, arr):
    self.arr = arr
    self.hmap = defaultdict(int)
    self.i = -1

  def hasNext(self):
    return self.i < len(self.arr) - 1

  def next(self):
    if self.hasNext():
      if self.arr[self.i + 1] not in self.hmap.keys():
        self.i += 1
        return self.arr[self.i]
      else:
        self.hmap[self.arr[self.i + 1]] -= 1
        if self.hmap[self.arr[self.i + 1]] == 0:
          del self.hmap[self.arr[self.i + 1]]
        self.i += 1
        return self.next() # checks for the next element

  def skip(self, val):
    self.hmap[val] += 1

itr = SkipIterator([2, 3, 5,5, 6, 5, 7, 5, -1, 5, 10])
print(itr.hasNext())  # true
print(itr.next())  # returns 2
itr.skip(5) # skips 5
itr.skip(6) # skips 5
print(itr.next())  # returns 3
print(itr.next())  # returns 5 because 5 should be skipped
print(itr.next())  # returns 5 because 6 should be skipped
itr.skip(5) # skip 5
itr.skip(5) # skip 5
print(itr.next())  # returns 7
print(itr.next())  # returns -1
print(itr.next())  # returns 10
print(itr.hasNext())  # false
print(itr.next())  # None
