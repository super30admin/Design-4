from collections import defaultdict

class SkipIterator:
    def __init__(self, iterator):
        self.iterator = iter(iterator)
        self.skip_map = defaultdict(int)
        self.next_element = None
        self._advance()

    def _advance(self):
        """Advance the iterator to the next non-skipped element"""
        self.next_element = None
        while True:
            try:
                next_val = next(self.iterator)
                if self.skip_map[next_val] > 0:
                    self.skip_map[next_val] -= 1
                else:
                    self.next_element = next_val
                    break
            except StopIteration:
                break

    def hasNext(self):
        return self.next_element is not None

    def next(self):
        if not self.hasNext():
            raise StopIteration("No more elements")
        current = self.next_element
        self._advance()
        return current

    def skip(self, val):
        if self.next_element == val:
            self._advance()
        else:
            self.skip_map[val] += 1
            
itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
print(itr.hasNext())   # True
print(itr.next())      # 2
itr.skip(5)
print(itr.next())      # 3
print(itr.next())      # 6 (skipped 5)
print(itr.next())      # 5
itr.skip(5)
itr.skip(5)
print(itr.next())      # 7
print(itr.next())      # -1
print(itr.next())      # 10
print(itr.hasNext())   # False
# itr.next()           # would raise StopIteration
