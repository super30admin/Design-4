class SkipIterator:
    
    def __init__(self, iterator):
        self.iter = iterator
        self.skipmap = dict()
        self.curr = None
        self.__advance()
        
    def hasNext(self): #O(1)
        return self.curr is not None
    
    def next(self): #O(1)
        c = self.curr 
        self.__advance()
        return c
    
    def skip(self, val): #O(1)
        if val == self.curr:
            self.__advance()
        else:
            self.skipmap[val] = self.skipmap.get(val,0) + 1
            
    def __advance(self):
        self.curr = None
        nextVal = next(self.iter, None)
        while self.curr is None and nextVal is not None:
            if nextVal in self.skipmap:
                self.skipmap[nextVal] -= 1
                if self.skipmap[nextVal] == 0: 
                    del self.skipmap[nextVal]
                nextVal = next(self.iter)
            else:
                self.curr = nextVal


itr = SkipIterator(iter([5,6,7,5,6,8,9,5,5,6,8]))
print(itr.hasNext())   # true
print(itr.next())      # returns 5
itr.skip(5)
print(itr.next())      # returns 6
print(itr.next())      # returns 7 
print(itr.next())      # returns 6 because 5 should be skipped
itr.skip(5)
itr.skip(5)
print(itr.next())      # returns 8
print(itr.next())      # returns 9
print(itr.next())      # returns 6
print(itr.next())      # return 8
print(itr.hasNext())   # returns false                