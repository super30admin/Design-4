"""
Time Complexity : O(n)
Space Complexity : O(n)
Did this code successfully run on Leetcode : Yes, was able to verify the output
Any problem you faced while coding this: none
"""

class SkipIterator:
    def __init__(self, it):
        self.it = it
        self.skipMap = {}
        self.next_ele = None
        self.advance()
        
    def next(self):
        temp = self.next_ele
        self.advance()
        return temp
        
    def hasNext(self):
        return self.next_ele != None
        
    def advance(self):
        self.next_ele = None
        while self.next_ele == None and self.it.__length_hint__() > 0:
            ele = next(self.it)
            if ele in self.skipMap:
                self.skipMap[ele] -= 1
                if self.skipMap[ele] == 0:
                    self.skipMap.pop(ele, None)
            else:
                self.next_ele = ele
            
    def skip(self, skipNum):
        if self.next_ele == skipNum:
            self.advance()
        else:
            if skipNum in self.skipMap:
                self.skipMap[skipNum] += 1
            else:
                self.skipMap[skipNum] = 1
                
                
itr = SkipIterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))
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

"""
RESULTS:

True
2
3
6
5
7
-1
10
False
None
"""