# TC:
# has_next: O(n)
# next: O(1)
# skip: O(1)

# SC: O(n)
class SkipIterator(Iterator):
    def __init__(self, it):
        self.it = it
        self.skip_map = collections.Counter()
        self.next_num = None

    def has_next(self):
        if self.next_num is not None:
            return True
        while self.it.has_next():
            next = self.it.next()
            if next not in self.skip_map or self.skip_map[next] == 0:
                self.next_num = next
                return True
            else:
                self.skip_map[next] -= 1
        return False

    def next(self):
        if not self.has_next():
            raise Exception('Next element is not present')
        if self.next_num is not None:
            next = self.next_num
            self.next_num = None
            return next
        return self.it.next()

    def skip(self, num):
        self.skip_map[num] += 1
