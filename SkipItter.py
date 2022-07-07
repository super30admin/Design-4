'''
time complexity: O(n)
space complexity: O(1)
'''
from unittest import skip


class SkipItear:
    def __init__(self,arr):
        self.skipDict = {}
        self.it = iter(arr)
        self.NextVal = next(self.it,None)

    def hasNext(self):
        return self.NextVal!=None

    def skipIt(self,val):
        if val not in self.skipDict:
            self.skipDict[val]=0
        self.skipDict[val]+=1

    def getnextVal(self):
        rtrVal = None
        while(self.NextVal!=None):
            if(self.NextVal in self.skipDict):
                self.skipDict[self.NextVal]-=1
                if(self.skipDict[self.NextVal]==0):
                    del self.skipDict[self.NextVal]
                self.NextVal = next(self.it,None)
            else:
                rtrVal = self.NextVal
                self.NextVal = next(self.it,None)
                break
        return rtrVal

itr = SkipItear([2, 3, 5, 6, 5, 7, 5, -1, 5, 5, 10])
#print(itr.hasNext())

#print(itr.nextEle)
print(itr.hasNext())
print(itr.getnextVal())
itr.skipIt(3)
itr.skipIt(5)

#print(itr.nextEle)
print(itr.getnextVal())