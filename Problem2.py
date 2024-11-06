# Google | Skip Iterator
from collections import defaultdict
from typing import Iterator, Optional

class SkipIterator:
    def __init__(self, iterator: Iterator[int]):
        """
        Initialize SkipIterator with an existing iterator
        
        Args:
            iterator: An iterator of integers
        """
        self.iterator = iterator
        self.skip_counts = defaultdict(int)  # value -> count of times to skip
        self.next_element = None  # Buffer for next element
        self._find_next()  # Initialize next_element
        
    def _find_next(self) -> None:
        """Helper method to find the next non-skipped element"""
        self.next_element = None
        try:
            while self.next_element is None:
                current = next(self.iterator)
                if self.skip_counts[current] > 0:
                    # Decrease skip count for this value
                    self.skip_counts[current] -= 1
                    if self.skip_counts[current] == 0:
                        del self.skip_counts[current]
                else:
                    self.next_element = current
        except StopIteration:
            pass
    
    def has_next(self) -> bool:
        """Return True if there are more elements to iterate"""
        return self.next_element is not None
    
    def next(self) -> Optional[int]:
        """Return the next non-skipped element"""
        if not self.has_next():
            raise StopIteration()
        
        result = self.next_element
        self._find_next()
        return result
    
    def skip(self, val: int) -> None:
        """
        Skip the next occurrence(s) of val
        
        Args:
            val: The value to skip
        """
        if self.next_element == val:
            self._find_next()
        else:
            self.skip_counts[val] += 1

