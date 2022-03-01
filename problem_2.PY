class Skipiterator:
    def __init__(self, iterator):
        self.nextEl = None
        self.it = iterator
        self.hashmap = {}
        self.advance()

    def hasNext(self):
        return self.nextEl is not None

    def next(self):
        temp = self.nextEl
        self.advance()
        return temp

    def skip(self, val):
        if self.nextEl == val:
            self.advance()
        else:
            self.hashmap[val] = self.hashmap.get(val, 0) + 1

    def advance(self):
        self.nextEl = None

        while self.nextEl is None:
            temp = next(self.it, None)
            if temp is None:
                break

            if temp in self.hashmap:
                self.hashmap[temp] = self.hashmap[temp] - 1
                if self.hashmap[temp] == 0:
                    del self.hashmap[temp]
            else:
                self.nextEl = temp


itr = Skipiterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))
print(itr.hasNext())  # true
print(itr.next())  # returns 2
itr.skip(5)
print(itr.next())  # returns 3
print(itr.next())  # returns 6 because 5 should be skipped
print(itr.next())  # returns 5
print(itr.skip(5))
print(itr.skip(5))
print(itr.next())  # returns 7
print(itr.next())  # returns -1
print(itr.next())  # returns 10
print(itr.hasNext())  # false
print(itr.next())  # error

# Output:
# True
# 2
# 3
# 6
# 5
# None
# None
# 7
# -1
# 10
# False
# None