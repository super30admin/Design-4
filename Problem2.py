## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)
from collections.abc import Iterator
from collections import Counter
class SkipIterator:
    def __init__(self, iterator: Iterator[int]) -> None:
        """
        Time Complexity: O(1) amortized. It calls advance(), which pulls from the iterator.
        Space Complexity: O(k), where k is the number of elements that have been skipped but not yet encountered
        in the iterator. The Counter dictionary only stores pending skips.
        """
        self.iterator = iterator
        # Hash map to track elements we need to skip in the future
        self.skipCount: Counter[int] = Counter()
        self.nextElement: None|int = None
        # Pre-fetch the very first valid element
        self.advance()
    
    def advance(self) -> None:
        """
        Advances the underlying iterator to the next valid element, 
        skipping elements if they are marked in skip_count.
        Time Complexity: O(1) amortized, O(N) in the worst case if a lot of elements are skipped
        """
        self.nextElement = None
        # Fetch the next element from the underlying iterator
        while True:
            val = next(self.iterator, None)

            # If the iterator is exhausted, break
            if val is None:
                break
            
            # If the current value needs to be skipped
            if self.skipCount[val] > 0:
                self.skipCount[val] -= 1
            else:
                # Found a valid element, cache it and break
                self.nextElement = val
                break

    def hasNext(self) -> bool:
        """
        Returns True if there is a next valid element.
        Time Complexity: O(1). Just checks if the cached value is None
        """
        return self.nextElement is not None

    def next(self) -> int|None:
        """
        Returns the next valid element.
        Time Complexity: O(1) amortized. The worst-case is O(N) if it has to skip a massive block of elements at once,
        but across the lifecycle of the iterator, each element is touched at most once.
        """
        if not self.hasNext():
            raise StopIteration('No more elements')

        result = self.nextElement
        # Prep the iterator for the subsequent call
        self.advance()
        return result
        
    
    def skip(self, val: int) -> None:
        """
        Marks an element to be skipped.
        Time Complexity: O(1) amortized. It either increments a hash map entry or triggers an advance()
        """
        # If the value to skip is already cached, skip it right now
        if self.nextElement == val:
            self.advance()
        else:
            # Otherwise, track it for future skipping
            self.skipCount[val] += 1


it = SkipIterator(iter([1,2,3,4,5]))
it.skip(1)
it.skip(5)
print(it.hasNext())
print(it.next())
print(it.next())
print(it.hasNext())
print(it.next())
print(it.hasNext())