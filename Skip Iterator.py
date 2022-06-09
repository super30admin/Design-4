class SkipIterator:
    
    def __init__(self,inputList):
        
        # initialize a skipDict
        self.skipDict = {}
        
        # convert nestedList to an iterator
        self.listIter = iter(inputList)
    
        # initialize nextEle
        self.nextEle = next(self.listIter,None)
                
    def hasNext(self):
        # returns boolean
        if self.nextEle == None:
            return False
        else:
            return True
    
    def rtrNext(self):
        # returns integer
        rtrVal = None
        
        while self.nextEle != None:
            
            # chk if the value is in skipDict
            if self.nextEle in self.skipDict:

                # reduce the count by -1
                self.skipDict[self.nextEle] = self.skipDict[self.nextEle] - 1

                if self.skipDict[self.nextEle] == 0:
                    # remove the key-val pair from the skipDict
                    del self.skipDict[self.nextEle]

                # update the native iterator ptr
                self.nextEle = next(self.listIter,None)
            
            else:
                rtrVal = self.nextEle
                self.nextEle = next(self.listIter,None)
                break
        
        return rtrVal
    
    def skip(self,val):
        # skips the value
        
        # 1. chk if val
        if self.nextEle == val:
            # we need to skip the ele
            self.nextEle = next(self.listIter,None)
        
        else:
            # we need to add it to the skipDict
            if val not in self.skipDict:
                self.skipDict[val] = 1
            
            else:
                self.skipDict[val] = self.skipDict[val] + 1

itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 5, 10])

itr.hasNext()

itr.skip(2)

itr.rtrNext()

itr.skip(5)

itr.skip(3)

itr.rtrNext()

itr.rtrNext()

itr.skip(5)

itr.skip(5)

itr.rtrNext()

itr.rtrNext()

itr.rtrNext()

itr.rtrNext()

itr.hasNext()

itr.rtrNext()
print(itr.rtrNext())
