from collections import defaultdict
from typing import Iterator

class SkipIterator:

    def __init__(self, it: Iterator[int]):
        self.itemsToSkip = {}
        self.it = iter(it)
        self.nextElement = None
        self.advance()
    
    def advance(self):
        while self.nextElement == None:
            try:
                element = next(self.it)
                if element in self.itemsToSkip:
                    self.itemsToSkip[element] -=1
                    if self.itemsToSkip[element] == 0:
                        del self.itemsToSkip[element]
                else:
                    self.nextElement = element
            except StopIteration:
                break

    def has_next(self) -> bool:
        return self.nextElement is not None

    def next(self) -> int:
        result = self.nextElement
        self.advance()
        return result

    def skip(self, val: int) -> None:
        """
        The input parameter is an int, indicating that the next element
        equals 'val' needs to be skipped. This method can be called multiple
        times in a row. skip(5), skip(5) means the next two 5s should be skipped.
        """
        if val not in self.itemsToSkip:
            self.itemsToSkip[val] = 0
        self.itemsToSkip[val]+=1
        