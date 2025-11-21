
"""
TC:
    • next(): Amortized O(1)
         Each element in the underlying iterator is processed once.  
         Skipped elements are consumed only once as well.  
         Advancement stops as soon as a valid element is found.
    • skip(): O(1)
         Uses a hashmap to track skip counts.
    • has_next(): O(1)
         Simply checks the buffered value.

SC: O(S)
    • A hashmap stores skip counts for values requested to be skipped.  
      In the worst case, S distinct values might be marked for skipping.  
    • Aside from this, only O(1) extra space is used for buffering.

Approach:
We are implementing a “Skip Iterator” — an iterator wrapper that allows us
to skip specific values on demand.  
This requires giving users the illusion of a normal iterator while internally
handling skip bookkeeping and buffering.

Key idea:
Maintain a **skip_map** (value → number of times to skip) and a **next_val** buffer.
The buffer always holds the next valid value that should be returned.

Steps:
1. Store the underlying iterator and initialize skip_map.
2. Prime the iterator by calling `_advance()` once so next_val is ready.
3. **has_next()** simply checks whether next_val exists.
4. **next()**:
       - Return next_val.
       - Call _advance() to compute the next valid buffered element.
5. **skip(val)**:
       - If val is already the next_val, consume it immediately via _advance().
       - Otherwise increment skip_map[val] to skip the *next* occurrence.
6. **_advance()**:
       - Pull elements from the underlying iterator until:
             • value is NOT in skip_map, OR  
             • skip_map says this value must be skipped (in which case decrement count).
       - Store the first valid value in next_val.

This systematic buffering and skip-count tracking ensures that:
    • Skips apply correctly to future occurrences.
    • Each element is consumed at most once.
    • The client experiences a clean iterator interface.

This implementation was validated through custom test cases.
"""

from collections import defaultdict
from typing import Iterator, Optional

class SkipIterator:
    def __init__(self, iterator: Iterator[int]):
        self.iterator = iterator
        self.skip_map = defaultdict(int)
        self.next_val = None
        
        # "Prime" the iterator to load the first valid element
        self._advance()

    def has_next(self) -> bool:
        """
        Returns true if there are elements left.
        """
        return self.next_val is not None

    def next(self) -> int:
        """
        Returns the next element. Raises StopIteration (or generic Exception) if empty.
        """
        if not self.has_next():
            raise StopIteration("No elements left")
        
        result = self.next_val
        self._advance() # Prepare the next value for the future call
        return result

    def skip(self, val: int) -> None:
        """
        Skips the next occurrence of val.
        """
        # Optimization: If the value we want to skip is ALREADY waiting 
        # in our buffer (next_val), we discard it immediately.
        if self.next_val == val:
            self._advance()
        else:
            self.skip_map[val] += 1

    def _advance(self) -> None:
        """
        Helper: Moves the underlying iterator forward until it finds 
        a value that shouldn't be skipped.
        """
        self.next_val = None # Reset buffer
        
        while True:
            try:
                candidate = next(self.iterator)
                
                # Check if this candidate is in our "to-skip" list
                if self.skip_map[candidate] > 0:
                    self.skip_map[candidate] -= 1
                    # Clean up map to save space if count hits 0
                    if self.skip_map[candidate] == 0:
                        del self.skip_map[candidate]
                else:
                    # Found a valid element! Store it and stop looking.
                    self.next_val = candidate
                    break
            except StopIteration:
                # Underlying iterator is empty
                break