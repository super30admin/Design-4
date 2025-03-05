# Time O(n)
# Space O(n) for skip map
from typing import List
class SkipIterator:
    
    def __init__(self, nums: List[int]):
        self.iterator = iter(nums)
        self.skipMap = {}
        self.nextE = None
        self.advance()
    
    def advance(self) -> None:
        self.nextE = None
        while self.nextE == None:
            try:
                inext = next(self.iterator)
                if inext in self.skipMap:
                    self.skipMap[inext] -= 1
                    if self.skipMap[inext] == 0: del self.skipMap[inext]
                else:
                    self.nextE = inext
            except StopIteration:
                break
    
    def nextItem(self) -> int:
        temp = self.nextE
        if temp == None: raise StopIteration("End of the list")
        self.advance()
        return temp
    
    def hasNext(self) -> bool:
        return True if self.nextE != None else False
        
    """
        The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
        This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
    """
    def skip(self, val: int) -> None:
        if val == self.nextE: self.advance()
        else:
            if val in self.skipMap: self.skipMap[val] += 1
            else: self.skipMap[val] = 1

        
itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
print(itr.hasNext()) # true
print(itr.nextItem()) # returns 2
print(itr.skip(5))
print(itr.nextItem()) #returns 3
print(itr.nextItem()) # returns 6 because 5 should be skipped
print(itr.nextItem()) # returns 5
print(itr.skip(5))
print(itr.skip(5))
print(itr.nextItem()) # returns 7
print(itr.nextItem()) # returns -1
print(itr.nextItem())# returns 10
print(itr.hasNext())# false
print(itr.nextItem()) # error