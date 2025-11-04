#Time Complexity: O(1)
#Space Complexity: O(K) -> number of skipped elements

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


# Test driver
itr = SkipIterator([5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8])
print(itr.hasNext())  # True
itr.skip(5)
print(itr.next())     # 6
itr.skip(5)
print(itr.next())     # 7
print(itr.next())     # 6
itr.skip(8)
itr.skip(9)
print(itr.next())     # 5
print(itr.next())     # 5
print(itr.next())     # 6
print(itr.hasNext())  # True
print(itr.next())     # 8
print(itr.hasNext())  # False

