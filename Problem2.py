## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.

# class SkipIterator implements Iterator<Integer> {

# 	public SkipIterator(Iterator<Integer> it) {
# 	}

# 	public boolean hasNext() {
# 	}

# 	public Integer next() {
# 	}

# 	/**
# 	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
# 	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
# 	*/ 
# 	public void skip(int val) {
# 	}
# }
# Example:

# SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
# itr.hasNext(); // true
# itr.next(); // returns 2
# itr.skip(5);
# itr.next(); // returns 3
# itr.next(); // returns 6 because 5 should be skipped
# itr.next(); // returns 5
# itr.skip(5);
# itr.skip(5);
# itr.next(); // returns 7
# itr.next(); // returns -1
# itr.next(); // returns 10
# itr.hasNext(); // false
# itr.next(); // error

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
