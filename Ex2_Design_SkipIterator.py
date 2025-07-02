# ----------------------------------------------------
# Intuition:
# We're designing an iterator that supports skipping elements.
# - We wrap around a normal iterator and add a `skip(val)` method.
# - We use a HashMap (skip_map) to count how many times each value should be skipped.
# - To keep `hasNext()` and `next()` efficient, we buffer the next valid element in `self.next_elem`.

# ----------------------------------------------------
# Optimal Approach (HashMap + Lazy Advancement)
#
# Time Complexity:
# - next(): Amortized O(1) → each element processed once
# - hasNext(): O(1)
# - skip(): O(1)
#
# Space Complexity: O(n)
# - for storing skip_map (at most all distinct skipped values)
# ----------------------------------------------------

from collections import defaultdict

class SkipIterator:
    def __init__(self, iterable):
        self.it = iter(iterable)
        self.skip_map = defaultdict(int)
        self.next_elem = None
        self._advance()

    def _advance(self):
        """
        Advance to the next valid (non-skipped) element.
        """
        self.next_elem = None
        while True:
            try:
                val = next(self.it)
                if self.skip_map[val] > 0:
                    self.skip_map[val] -= 1
                else:
                    self.next_elem = val
                    return
            except StopIteration:
                return

    def hasNext(self) -> bool:
        return self.next_elem is not None

    def next(self) -> int:
        if not self.hasNext():
            raise StopIteration("No more elements.")
        result = self.next_elem
        self._advance()
        return result

    def skip(self, val: int) -> None:
        if self.next_elem == val:
            self._advance()
        else:
            self.skip_map[val] += 1

# ----------------------------------------------------
# Example Usage
# ----------------------------------------------------
if __name__ == "__main__":
    itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
    print(itr.hasNext())  # True
    print(itr.next())     # 2
    itr.skip(5)
    print(itr.next())     # 3
    print(itr.next())     # 6
    print(itr.next())     # 5
    itr.skip(5)
    itr.skip(5)
    print(itr.next())     # 7
    print(itr.next())     # -1
    print(itr.next())     # 10
    print(itr.hasNext())  # False
