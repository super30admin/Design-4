class SkipIterator:
    def __init__(self, iterator):
        self.iterator = iterator
        self.ptr = -1
        self.map = {}

    def hasNext(self):
        if self.ptr + 1 >= len(self.iterator):
            return False
        else:
            return True

    def next(self):
        if self.hasNext():
            self.ptr += 1
            if self.iterator[self.ptr] in self.map:
                if self.map[self.iterator[self.ptr]] > 0:
                    self.map[self.iterator[self.ptr]] -= 1
                    self.ptr += 1
            return self.iterator[self.ptr]
        else:
            return None

    def skip(self, num):
        self.map[num] = self.map.get(num, 0) + 1
    

itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])

print(itr.hasNext()) # true
print(itr.next()) # returns 2
itr.skip(5)
print(itr.next()) # returns 3
print(itr.next()) # returns 6 because 5 should be skipped
print(itr.next()) # returns 5
itr.skip(5)
itr.skip(5)
print(itr.next()) # returns 7
print(itr.next()) # returns -1
print(itr.next()) # returns 10
print(itr.hasNext()) # false
print(itr.next()) #error 





