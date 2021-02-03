# Skip Iterator
class SkipIterator:
    def __init__(self, nums):
        self.skipList = {}
        self.i = 0
        self.nums = nums
    
    def hasNext(self):
        self.advance()
        if(self.i>=len(self.nums)):
            return False
        return True
    
    def next(self):
        self.advance()
        if(self.i>=len(self.nums)):
            return -1*float("inf")
        else:
            self.i+=1
            return self.nums[self.i-1]
    
    def skip(self, n):
        if(n in self.skipList.keys()):
            self.skipList[n] += 1
        else:
            self.skipList[n] = 1
    
    def advance(self):
        while(self.i<len(self.nums) and self.nums[self.i] in self.skipList.keys()):
            self.skipList[self.nums[self.i]] -= 1
            if(self.skipList[self.nums[self.i]]==0):
                del self.skipList[self.nums[self.i]]
            self.i += 1


itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
print(itr.hasNext())
print(itr.next())
itr.skip(5)
print(itr.next())
print(itr.next())
print(itr.next())
itr.skip(5)
itr.skip(5)
print(itr.next())
print(itr.next())
print(itr.next())
print(itr.hasNext())
print(itr.next())
