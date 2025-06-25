"""
Use hashmap to track counts of values to skip
Maintain `nextEl` buffer to always hold next valid element
Move to next valid element during `next()` and `hasNext()`
"""
"""
Time Complexity: next() and hasNext() - amortized O(1) ; skip() - O(1)
Space Complexity : Skip map: O(S) - S = number of unique skipped values
Buffer (nextEl): O(1)
"""


class skipIterator:
    def __init__(self, iterator):
        self.iterator = iterator
        self.skipMap = {}  
        self.nextEl = None
        self._advance()

    def _advance(self):
        self.nextEl = None
        while True:
            try:
                el = next(self.iterator)
                if el in self.skipMap:
                    self.skipMap[el] -= 1
                    if self.skipMap[el] == 0:
                        del self.skipMap[el]
                else:
                    self.nextEl = el
                    return
            except StopIteration:
                return

    def hasNext(self) -> bool:
        return self.nextEl is not None

    def next(self) -> int:
        if not self.hasNext():
            raise Exception("No more elements")
        res = self.nextEl
        self._advance()
        return res

    def skip(self, val: int) -> None:
        if self.nextEl == val:
            self._advance()
        else:
            self.skipMap[val] = self.skipMap.get(val, 0) + 1


if __name__ == "__main__":
    it = skipIterator(iter([5, 6, 7, 5, 6, 8, 9]))
    print(it.next())     
    it.skip(6)
    print(it.next())     
    print(it.next())     
    print(it.next())     
    it.skip(8)
    print(it.next())     
    print(it.hasNext())  
