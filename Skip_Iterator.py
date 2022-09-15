from collections import defaultdict


class SkipIterator:
    def __init__(self, iterator):
        self.hmap = defaultdict(int)
        self.ptr = -1
        self.iterator = iterator

    def hasNext(self):  # TC - O(1)
        if self.ptr+1 > len(self.iterator):
            return False
        return True

    def next(self):  # TC - O(n)
        if self.hasNext():
            self.ptr += 1
            if self.hmap[self.iterator[self.ptr]] > 0:
                self.hmap[self.iterator[self.ptr]] -= 1
                self.ptr += 1
            return self.iterator[self.ptr]
        return None

    def skip(self, num):  # TC - O(1)
        self.hmap[num] += 1
