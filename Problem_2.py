class SkipIterator:
    def __init__(self, inputList):
        self.skipMap = {}
        self.listIter = iter(inputList)
        self.nextEl = next(self.listIter, None)
        
    def hasNext(self):
        if self.nextEl == None:
            return False
        else:
            return True
        
    def advance(self):
        nextVal = None
        
        while self.nextEl != None:
            if self.nextEl in self.skipMap:
                cnt = self.skipMap[self.nextEl]
                self.skipMap[self.nextEl] = cnt - 1
                
                if self.skipMap[self.nextEl] == 0:
                    del self.skipMap[self.nextEl]
                
                self.nextEl = next(self.listIter, None)
            else:
                nextVal = self.nextEl
                self.nextEl = next(self.listIter, None)
                break
        return nextVal
        
    def skip(self, val):
        if self.nextEl == val:
            self.nextEl = next(self.listIter, None)
        
        else:
            if val not in self.skipMap:
                self.skipMap[val] = 1
            else:
                cnt = self.skipMap[val]
                self.skipMap[val] = cnt + 1
                
                
itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 5, 10])

itr.hasNext()

itr.skip(2)

itr.advance

itr.skip(5)

itr.skip(3)

itr.advance()

itr.advance()

itr.skip(5)

itr.skip(5)

itr.advance()

itr.advance()

itr.advance()

itr.advance()

itr.hasNext()

itr.advance()
print(itr.advance())