class SkipIterator:
    def __init__(self, iterator):
        self.it = iter(iterator)
        self.skip_map = {}
        self.next_el = None
        self._advance()

    def hasNext(self):
        return self.next_el is not None

    def next(self):
        if not self.hasNext():
            raise StopIteration("No more elements")
        el = self.next_el
        self._advance()
        return el

    def skip(self, num):
        if not self.hasNext():
            raise StopIteration("No more elements")
        if self.next_el == num:
            self._advance()
        else:
            self.skip_map[num] = self.skip_map.get(num, 0) + 1

    def _advance(self):
        self.next_el = None
        while self.next_el is None:
            try:
                el = next(self.it)
                if el not in self.skip_map:
                    self.next_el = el
                else:
                    self.skip_map[el] -= 1
                    if self.skip_map[el] == 0:
                        del self.skip_map[el]
            except StopIteration:
                break

if __name__ == "__main__":
    it = SkipIterator([1, 2, 3])
    print(it.hasNext())  # True
    it.skip(2)
    it.skip(1)
    it.skip(3)
    print(it.hasNext())  # False