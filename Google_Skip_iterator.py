# Tiime Complexity : All method with O(1) except hasNext with O(N)
# Space Complexity : O(M), where M is the number of unique elements that need to be skipped.
from typing import Iterator

class SkipIterator:
    def __init__(self, iterator: Iterator[int]):
        self.iterator = iterator
        self.peek = None
        self.skipMap = {}

    def hasNext(self) -> bool:
        if self.peek is not None:
            return True

        while self.iterator.hasNext():
            num = self.iterator.next()
            if num not in self.skipMap:
                self.peek = num
                return True
            self.skipMap[num] -= 1
            if self.skipMap[num] == 0:
                del self.skipMap[num]
        return False

    def next(self) -> int:
        if self.hasNext():
            num = self.peek
            self.peek = None
            return num
        else:
            return -1

    def skip(self, val: int) -> None:
        if val in self.skipMap:
            self.skipMap[val] += 1
        else:
            self.skipMap[val] = 1
