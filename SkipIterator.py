"""
Time Complexity : O(n)
Space Complexity : O(n)
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : No
"""
class SkipIterator:
    def __init__(self,array):
        self.array = array
        self.hashMap = {}
        self.it = -1
    def hasNext(self):
        return self.it < len(self.array) - 1
    def next(self):
        if self.hasNext:
            if self.array[self.it] not in self.hashMap:
                self.it += 1
                return self.array[self.it]
            else:
                self.hashMap[self.array[self.it+1]] -= 1
                if self.hashMap[self.array[self.it+1]] == 0:
                    del self.self.hashmap[self.array[self.it+1]]
                self.it+=2
                return self.array[self.i]

    def skip(self,val):
        self.hashMap[val] += 1

It = SkipIterator([5,6,7,7,7,8,9,10,11])
print(It.next())
It.skip(7)
It.skip(7)
It.skip(7)
print(It.next())
print(It.hasNext())
print(It.next())
print(It.next())