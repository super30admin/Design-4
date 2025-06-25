# Approach:
# - Wrap a given iterator and allow skipping specified values via a `skip(val)` method.
# - Internally track the next valid element (`nextEl`) using `_advance()`.
# - `_advance()` skips values present in `skip_map` (a hash map of values → skip count).
# - `next()` returns the current valid value and then advances.
# - `hasNext()` checks if there's a next valid element.

# Time Complexity:
# - next(): Amortized O(1) per element, each element is advanced at most once.
# - hasNext(): O(1)
# - skip(val): O(1)

# Space Complexity:
# - O(k), where k is the number of distinct skipped elements (size of skip_map).

class SkipIterator:
    def __init__(self, iterator):
        self.it = iterator
        self.skip_map = {}
        self.nextEl = None
        self._advance()

    def _advance(self):
        self.nextEl = None
        while True:
            try:
                el = next(self.it)
                if el in self.skip_map:
                    self.skip_map[el] -= 1
                    if self.skip_map[el] == 0:
                        del self.skip_map[el]
                    continue
                self.nextEl = el
                return
            except StopIteration:
                return

    def hasNext(self):
        return self.nextEl is not None

    def next(self):
        if not self.hasNext():
            raise StopIteration()
        res = self.nextEl
        self._advance()
        return res

    def skip(self, val):
        if self.nextEl == val:
            self._advance()
        else:
            self.skip_map[val] = self.skip_map.get(val, 0) + 1

def main():
    it = iter([2, 3, 5, 6, 5, 7, 5, 8, 9, 5])
    skip_it = SkipIterator(it)

    print(skip_it.hasNext())   # True
    print(skip_it.next())      # 2

    skip_it.skip(5)            # skip next 5
    print(skip_it.next())      # 3

    print(skip_it.next())      # 6

    print(skip_it.next())      # 5 (second 5; first was skipped)

    skip_it.skip(5)            # skip next 5
    skip_it.skip(5)            # skip next 5

    print(skip_it.next())      # 7
    print(skip_it.next())      # 8
    print(skip_it.next())      # 9
    print(skip_it.hasNext())   # False

main()
