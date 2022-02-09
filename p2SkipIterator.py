#time: O(1)
#Space: O(N) N is number of skip iterators pending

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