# The SkipIterator class efficiently iterates over a list while allowing certain values to be skipped using a 
# hashmap (skip_map). The _advance() method ensures that the next valid element is always precomputed before next() is called.

# next() → O(1) on average, but worst-case O(n) if skipping multiple elements.
# skip() → O(1) (just updates the hashmap).
# has_next() → O(1) (checks a variable).
from collections import defaultdict
from typing import Iterator

class SkipIterator:
    def __init__(self, iterator: Iterator[int]):
        self.iterator = iterator  # The original list iterator
        self.skip_map = defaultdict(int)  # Dictionary to track skipped values
        self.next_el = None  # Stores the next valid element
        self._advance()  # Move to the first valid element

    def _advance(self):
        """Move to the next valid element, skipping unwanted ones."""
        self.next_el = None
        while True:
            try:
                num = next(self.iterator)  # Get the next number
                if self.skip_map[num] > 0:
                    self.skip_map[num] -= 1  # Decrease the skip count
                else:
                    self.next_el = num
                    break  # Found a valid number
            except StopIteration:
                break  # No more numbers left

    def has_next(self) -> bool:
        """Returns True if there's a valid next element, False otherwise."""
        return self.next_el is not None

    def next(self) -> int:
        """Returns the next valid number and moves forward."""
        result = self.next_el
        self._advance()  # Move to the next valid element
        return result

    def skip(self, val: int):
        """Skips the next occurrence of `val` in the sequence."""
        if self.next_el == val:
            self._advance()  # If the next number is the one to be skipped, move forward
        else:
            self.skip_map[val] += 1  # Otherwise, store it in the skip map

# Sample input list
nums = iter([1, 2, 3, 5, 5, 6, 7])
skip_iter = SkipIterator(nums)

# Running test cases
output = []
output.append(skip_iter.next())  # 1
skip_iter.skip(5)                # Skip the first 5
output.append(skip_iter.next())  # 2
output.append(skip_iter.next())  # 3
output.append(skip_iter.next())  # 6 (5 was skipped)
output.append(skip_iter.next())  # 7
output.append(skip_iter.has_next())  # False (No more elements)

print(output)
