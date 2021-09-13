#skip iterator
from collections import defaultdict
class SkipIterator:
    def __init__(self,array):
        self.array = array
        self.hashmap = defaultdict(int)
        self.i=-1
        
    def hasNext(self):
        return self.i<len(self.array)-1
        
    def next(self):
        if self.hasNext():
            if self.array[self.i+1] not in self.hashmap.keys():
                self.i+=1
                return self.array[self.i]
            else:
                self.hashmap[self.array[self.i+1]]-=1
                if self.hashmap[self.array[self.i+1]]==0:
                    del self.hashmap[self.array[self.i+1]]
                self.i+=2
                return self.array[self.i]
                    
    
    def skip(self,val):
        self.hashmap[val]+=1