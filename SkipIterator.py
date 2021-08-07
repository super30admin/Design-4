# Time Complexity : O(n)
# Space Complexity : O(n)

class SkipIterator:
    def _init__(self,arr):
        self.i = 0
        self.ign = {}
        self.arr = arr

    def hasNext(self):
        if self.i >= len(self.arr):
            return False
        self.i += 1
        while self.i <= len(self.arr):
            if self.arr[self.i] in self.ign:
                self.ign[self.arr[self.i]] -= 1
                if self.ign[self.arr[self.i]] == 0:
                    del self.ign[self.arr[self.i]]
                self.i += 1
            else:
                return True
        return False

    def next(self):
        return self.arr[self.i]

    def skip(self,val):
        self.ign[val] = True