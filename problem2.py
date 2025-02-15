"""
1. Let us maintain a hashmap for values to skip and their counts
2. Now when we start the skip iteration, everytime the parent class iterator's hasnext method returns true,
we prefetch the value to our next class var
3. In case there is no next value set then we do the prefetch the next value, check if it is must be skipped using skip Hashmap,
based on outcome we set the value to None or the element.
4. If the value was to be skipped we continue traversing until an element is reached, else we ran out of elements so False
5. In our next method, we return the prefetched element or raise an exception based on hasnext's outcome

TC: O(1)
SC: O(n) -> Hashmap
"""

import collections


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
            raise Exception("Error, no next element found")

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
