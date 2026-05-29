class SkipIterator:
    def __init__(self, it):
        self.nit = iter(it)
        self.skipMap = {}
        self.nextEl = None
        self.advance()

    def advance(self):
        self.nextEl = None
        while True:
            try:
                el = next(self.nit)
                if el not in self.skipMap:
                    self.nextEl = el
                    break
                else:
                    self.skipMap[el] -= 1
                    if self.skipMap[el] == 0:
                        del self.skipMap[el]
            except StopIteration:
                break

    def hasNext(self):
        return self.nextEl is not None

    def next(self):
        el = self.nextEl
        self.advance()
        return el

    def skip(self, val):
        if val == self.nextEl:
            self.advance()
        else:
            self.skipMap[val] = self.skipMap.get(val, 0) + 1

#Time: next() and skip() are amortized O(1)
#Space: O(k) where k is the number of skipped elements tracked in skipMap