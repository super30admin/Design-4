# tc O(1), sc O(n).
class Iterator:
    def __init__(self, data):
        self.data = data
        self.index = 0

    def has_next(self):
        return self.index < len(self.data)

    def next(self):
        if not self.has_next():
            raise StopIteration
        value = self.data[self.index]
        self.index += 1
        return value


class SkipIterator(Iterator):
    def __init__(self, it):
        self._it = it
        self._next = None
        self._skip = collections.Counter()

    def has_next(self):
        if self._next is not None:
            return True

        while self._it.has_next():
            next = self._it.next()
            if next not in self._skip or self._skip[next] == 0:
                self._next = next
                return True
            else:
                self._skip[next] -= 1
        return False

    def next(self):
        if not self.has_next():
            raise Exception("Next element is not found")

        if self._next is not None:
            next = self._next
            self._next = None
            return next

    def skip(self, val):
        self._skip[val] += 1


myitr = Iterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
itr = SkipIterator(myitr)
print(itr.has_next())
print(itr.next())
print(itr.skip(5))
print(itr.next())
print(itr.next())
print(itr.next())
print(itr.skip(5))
print(itr.skip(5))
print(itr.next())
print(itr.next())
print(itr.next())
print(itr.has_next())
try:
    itr.next()
except:
    print("Error")