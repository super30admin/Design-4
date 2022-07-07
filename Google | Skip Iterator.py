""""// Time Complexity : hasnext - O(1), skip and rtnNext - O(n)
// Space Complexity :O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :
"""


class SkipIterator:

    def __init__(self,inputList):
        self.skipmap={}
        self.iterList=iter(inputList)
        self.nextEl=next(self.iterList, None)



    def hasNext(self):
        if self.nextEl==None:
            return False
        else:
            return True


    def rtnNext(self):
        returnVal=None
        while self.nextEl:
            if self.nextEl in self.skipmap:
                self.skipmap[self.nextEl]-=1
                if self.skipmap[self.nextEl]==0:
                    del self.skipmap[self.nextEl]
                self.nextEl = next(self.iterList, None)
            else:
                returnVal=self.nextEl
                self.nextEl = next(self.iterList, None)
                break
        return returnVal



    def skip(self,val):
        if self.nextEl==val:
            self.nextEl=next(self.iterList, None)
        else:
            if val not in self.skipmap:
                self.skipmap[val]=1
            else:
                self.skipmap[val]+=1


itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 5, 10])

print(itr.hasNext())#True

itr.skip(2)

print(itr.rtnNext())#3

itr.skip(5)

itr.skip(3)

print(itr.rtnNext())#6

print(itr.rtnNext())#5

itr.skip(5)

itr.skip(5)

print(itr.rtnNext())#7

print(itr.rtnNext())#-1

print(itr.rtnNext())#5
print(itr.rtnNext())#10

print(itr.hasNext())#False

print(itr.rtnNext())#None
print(itr.rtnNext())#None