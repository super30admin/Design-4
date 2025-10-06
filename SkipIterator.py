#Time complexity: O(1) for hasNext, others are O(n) as they call the advance function which is O(n). Space is O(n) as well as we are using the skipMap
#Space Complexity: Space is O(n) as well as we are using the skipMap
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# The intuition here it to put future skip elements into the skip map. However, if we find the curr skip element as next element then we immediately advance and don't have to store into the skip map

class SkipIterator:

    def __init__(self, it):
        self.nit = iter(it)
        self.nextEl = None
        self.skipMap = {}
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
        if self.nextEl != None:
            return True
        return False

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
print(itr.next())  # 6
itr.skip(5)
print(itr.next())  # 7
print(itr.next())  # 6
itr.skip(8)
itr.skip(9)
print(itr.next())  # 5
print(itr.next())  # 5
print(itr.next())  # 6
print(itr.hasNext())  # True
print(itr.next())  # 8
print(itr.hasNext())  # False