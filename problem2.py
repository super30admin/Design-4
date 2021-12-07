class SkipIterator():
    def __init__(self, it):
        self.it = it
        self.idx = 0
        self.hashmap = dict()

    def has_next(self) -> None:
        # index is out of bound
        if self.idx == len(self.it):
            return False
        else:
            # current element is has to be skipped
            if self.it[self.idx] in self.hashmap and self.hashmap[self.it[self.idx]] > 0:
                self.hashmap[self.it[self.idx]] -= 1
                self.idx += 1
                self.has_next()
            return True

    def next(self) -> int:
        if self.has_next():
            temp = self.it[self.idx]
            self.idx += 1
            return temp
        else:
            print('No next possible')

    def skip(self, num) -> None:
        if not self.hashmap.get(num):
            self.hashmap[num] = 1
        else:
            self.hashmap[num] += 1