# The SkipIterator class extends an iterator, allowing specific values to be skipped during iteration.

# Initialization:
# - Wraps the input iterator in `_it`.
# - Uses `_skip` (a Counter) to track values to skip and `_next` to cache the next valid element.

# has_next:
# - Checks if there is a next valid element.
# - Iterates through `_it` until a value not in `_skip` is found or the iterator is exhausted.
# - Caches the valid element in `_next` and returns True, or False if none exist.

# next:
# - Calls has_next to ensure there is a next element.
# - If `_next` is cached, returns it and clears `_next`.
# - Otherwise, retrieves the next value from `_it`.

# skip:
# - Increments the count of the value to be skipped in `_skip`.

# TC:
# - has_next: O(k), where k is the number of skipped elements.
# - next: O(k) for checking and retrieving the next valid element.
# - skip: O(1) for adding to `_skip`.

# SC: O(n) - Space for the `_skip` Counter to track skipped values.


import collections
from typing import Iterator


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