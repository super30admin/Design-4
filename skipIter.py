# TC Noted next to each function below
# SC O(n) if n is size of input and we want to skip all n elements and n is distinct. 
# Else O(k) where k is subset of n elements that we want to skip, which gets added to Skipmap
class SkipIterator:
    
    def __init__(self, iterator):
        self.iter = iterator
        self.skipmap = dict() # we need a map because skip value can be sent multiple times, we need its count
        self.curr = None # initialize curr to first element
        self.__advance()  # advance to the next valid index
        
    def hasNext(self): #O(1)
        return self.curr is not None
    
    def next(self): #O(1)
        c = self.curr # save result
        self.__advance() # move curr to next valid location
        # print(c, self.curr)
        return c
    
    def skip(self, val): #O(1)
        if val == self.curr:
            self.__advance()
        else:
            self.skipmap[val] = self.skipmap.get(val,0) + 1
            
    def __advance(self): # O(n) in worst case, else O(1) on avg
        self.curr = None
        nextVal = next(self.iter, None)
        # while curr element is None, and next value is not None, keep advancing
        # exit loop as soon as element is set for self.curr
        while self.curr is None and nextVal is not None:
            # if the value is in skipMap, reduce its count by 1
            if nextVal in self.skipmap:
                self.skipmap[nextVal] -= 1
                if self.skipmap[nextVal] == 0: # if count reaches 0 then remove it
                    del self.skipmap[nextVal]
                nextVal = next(self.iter)
            else: # when value is no longer in the skip map, set that as curr value, and it will exit the loop
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

