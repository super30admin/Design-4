# O(K) space and O(1) time

from collections import Counter
class SkipIterator(Iterator):
  def __init__(self, it):
    self._it = it
    self._skip = Counter()
    self._next = None

  def skip(self, num):
    self._skip[num] += 1

  def has_next(self):
    if self._next is not None:
      return True
    while self._it.has_next():
      next = self._it.next()
      if next not in self._skip or self._skip[next] == 0:
        self._next = next
        return True
      else:
        self._skip[next] -= 1
    return False

  def next(self):
    if not self.has_next():
      raise Exception('no next')
    if self._next is not None:
      next = self._next
      self._next = None
      return next
    return self._it.next()
