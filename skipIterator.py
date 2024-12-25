"""
Tc: O(1), O(k) when k consecutive elements needs to be skipped
SP: O(n): to store n number of elements to be skipped

"""

class SkipIterator:
    def __init__(self, it):
        self.it = iter(it)
        self.skipMap = defaultdict(int)
        self.nextEl = next(self.it, None)
    def hasNext(self):
        return self.nextEl is not None
            
    def next(self):
        while self.nextEl in self.skipMap:
            self.skipMap[self.nextEl]-=1
            if self.skipMap[self.nextEl]==0:
                del self.skipMap[self.nextEl]
            self.nextEl =  next(self.it, None)
        ans =  self.nextEl
        self.nextEl =  next(self.it, None)
        return ans
    def skip(self, val):
        self.skipMap[val]+=1
        
# itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
# print(itr.hasNext())
# print(itr.next())
# print(itr.skip(5))
# print(itr.next())
# print(itr.next())
# print(itr.next())
# print(itr.skip(5))
# print(itr.skip(5))
# print(itr.next())
# print(itr.next())
# print(itr.next())
# print(itr.hasNext())
# print(itr.next())
iterator = SkipIterator([7, 7, 7, 8, 9])
iterator.skip(7)        # Skip the first 7
iterator.skip(7)        # Skip the second 7
iterator.skip(7)        # Skip the third 7
print(iterator.next())  # Output: 8
print(iterator.next())  # Output: 9
print(iterator.hasNext())  # Output: False
