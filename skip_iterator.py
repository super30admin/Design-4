# Time complexity: O(1) for hasNext, next, and skip
# Space complexity: O(n) for the skip_map

class SkipIterator:
    """
    An iterator that supports skipping specific values.

    Attributes:
        iterator (iterator): The underlying iterator.
        skip_map (dict): A map of values to skip and their counts.
        _next (any): The next value in the iterator.
    """

    def __init__(self, iterator):
        """
        Initializes the SkipIterator.

        Args:
            iterator (iterator): The underlying iterator.
        """
        self.iterator = iterator
        self.skip_map = {}
        self._next = None
        self._advance()

    def _advance(self):
        """
        Advances the iterator to the next value that is not skipped.
        """
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
        """
        Returns whether there is a next value in the iterator.

        Returns:
            bool: True if there is a next value, False otherwise.
        """
        return self._next is not None

    def next(self):
        """
        Returns the next value in the iterator.

        Returns:
            any: The next value in the iterator.

        Raises:
            StopIteration: If there are no more values in the iterator.
        """
        if not self.hasNext():
            raise StopIteration()
        result = self._next
        self._advance()
        return result

    def skip(self, val):
        """
        Skips the next occurrence of the specified value.

        Args:
            val (any): The value to skip.
        """
        if self._next == val:
            self._advance()
        else:
            self.skip_map[val] = self.skip_map.get(val, 0) + 1
