# Time Complexity : O(1) for hasNext, next, skip
# Space Complexity : O(n) for skip_map
# Any problem you faced while coding this : No

class SkipIterator:
	def __init__(self, iterator):
		self.iterator = iterator
		self.skip_map = {}
		self._next = None
		self._advance()

	def _advance(self):
		self._next = None
		while True:
			try:
				nxt = next(self.iterator)
				if nxt in self.skip_map and self.skip_map[nxt] > 0:
					self.skip_map[nxt] -= 1
					if self.skip_map[nxt] == 0:
						del self.skip_map[nxt]
					continue
				self._next = nxt
				break
			except StopIteration:
				break

	def hasNext(self):
		return self._next is not None

	def next(self):
		if not self.hasNext():
			raise StopIteration()
		result = self._next
		self._advance()
		return result

	def skip(self, val):
		if self._next == val:
			self._advance()
		else:
			self.skip_map[val] = self.skip_map.get(val, 0) + 1