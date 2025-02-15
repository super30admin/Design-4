from collections import defaultdict


class SkipIterator:
    def __init__(self, nums):
        self.iterator = iter(nums)  # Internal iterator
        self.skip_map = defaultdict(int)  # Keeps track of elements to skip
        self.next_elem = None
        self.advance()  # Move to the first valid element

    def advance(self):
        """Moves the iterator to the next non-skipped element."""
        self.next_elem = None
        while self.next_elem is None:
            try:
                num = next(self.iterator)
                if self.skip_map[num] > 0:
                    self.skip_map[num] -= 1  # Reduce skip count
                else:
                    self.next_elem = num
            except StopIteration:
                break  # End of iterator

    def hasNext(self):
        return self.next_elem is not None

    def next(self):
        if not self.hasNext():
            raise StopIteration("No more elements")

        res = self.next_elem  # Store current valid element
        self.advance()  # Move to the next valid element
        return res

    def skip(self, val):
        """Marks the next occurrence of 'val' to be skipped."""
        if self.next_elem == val:
            self.advance()  # Skip immediately if next element is `val`
        else:
            self.skip_map[val] += 1  # Schedule a future skip
