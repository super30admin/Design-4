# Time Complexity : 
#   next(): O(1) amortized, each element is processed at most once
#   has_next(): O(1) amortized per call, may skip elements but each skipped element is processed once
#   skip(num): O(1)
# Space Complexity : O(M), M = number of unique elements to skip
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Explanation:
# We implement a SkipIterator that wraps a normal iterator and allows skipping elements.
# - _it: underlying iterator
# - _skip: counter of elements to skip
# - _next: stores the next valid element
# Methods:
# - has_next(): advances the underlying iterator until we find a valid element
# - next(): returns _next if available, else the next element from iterator
# - skip(num): increments skip count for a value

class SkipIterator(Iterator):
  def __init__(self, it):
    self._it = it
    self._skip = collections.Counter()
    self._next = None

  def has_next(self):
    # there is still an unprocessed next
    if self._next is not None:
      return True
    # fill in self._next, unless reached end
    while self._it.has_next():
      next = self._it.next()
      if next not in self._skip or self._skip[next] == 0:
        self._next = next
        return True
      else:
        self._skip[next] -= 1
    return False

  def next(self):
    # this check is not needed if guaranteed to call has_next before next
    if not self.has_next():
      raise Exception('called next but has no next')
    if self._next is not None:
      next = self._next
      self._next = None
      return next
    return self._it.next()

  def skip(self, num):
    self._skip[num] += 1