#Time Complexity :- O(1) hasNext
#Space Complexity :- O(m) where m is the number of unique numebr skips asked to add in hashMap

class SkipIterator:
    def __init__(self,arr):
        self.arr = arr
        self.hashmap = {}
        self.i=-1

    def hasNext(self):
        return self.i<len(self.arr)-1

    def next(self):
        if self.hasNext():
            if self.arr[self.i+1] not in self.hashmap.keys():
                self.i+=1
                return self.arr[self.i]
            else:
                self.hashmap[self.arr[self.i+1]]-=1
                if self.hashmap[self.arr[self.i+1]]==0:
                    del self.hashmap[self.arr[self.i+1]]
                self.i+=2
                return self.arr[self.i]


    def skip(self,val):
        self.hashmap[val]+=1

