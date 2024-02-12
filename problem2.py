#Time Complexity : O(n)
#Space Complexity :O(n)

from collections import defaultdict

class SkipIterator:
    def __init__(self, it):
        self.it = it
        self.skip_count = defaultdict(int)
        self.nextElement = None
        self.advance()

    def hasNext(self):
        return self.nextElement is not None

    def next(self):
        if not self.hasNext():
            raise StopIteration()
        result = self.nextElement
        self.advance()
        return result

    def skip(self, val):
        if self.nextElement == val:
            self.advance()
        else:
            self.skip_count[val] += 1

    def advance(self):
        self.nextElement = None
        while self.nextElement is None:
            if not self.it.has_next():
                return
            element = self.it.next()
            if self.skip_count[element] > 0:
                self.skip_count[element] -= 1
            else:
                self.nextElement = element
