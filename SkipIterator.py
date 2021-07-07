class SkipIterator():
    """Time complexity-O(n) as in worst case we may skip all elements
    Space complexity-O(n) as we are using hashmap which consist of all skip elemnets...which in worst case can be n"""
    def __init__(self, nums):
        self.nextele=None
        self.map={}
        self.result=iter(nums)
        self.advance()
        
        
    def hasNext(self):
        if self.nextele:
            return True
        return False
    
    def next(self):
        temp=self.nextele
        self.advance()
        return temp
        
    def skip(self, num):
        if num ==self.nextele:
            self.advance()
        else:
            if num not in self.map:
                self.map[num]=1
            else:
                self.map[num]+=1
        
    def advance(self):
        self.nextele=None
        nextiter=next(self.result, None)
        while self.nextele==None and nextiter!=None:
            if nextiter not in self.map:
                self.nextele=nextiter
            else:
                self.map[nextiter]-=1
                if self.map[nextiter]==0:
                    self.map.pop(nextiter)
                nextiter=next(self.result)
                
        
itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
print(itr.hasNext())
print(itr.next())
print(itr.skip(5))
print(itr.next())
print(itr.next())
print(itr.next())
print(itr.skip(5))
print(itr.skip(5))
print(itr.next())
print(itr.next())
print(itr.next())
print(itr.hasNext())
print(itr.next())  