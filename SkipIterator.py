from typing import Optional, List, Iterator
class SkipIterator:
    def __init__(self, it):
        self.it = it
        self.skipMap ={}
        self.nextEle = None
        self.advance()

    def advance(self):
        self.nextEle = None
        while True:
            try:
                next_val = next(self.it)
                if next_val in self.skipMap:
                    self.skipMap[next_val]-=1
                    if self.skipMap[next_val] == 0:
                        self.skipMap.pop(next_val, None)
                else:
                    self.nextEle = next_val
                    break
            except StopIteration:
                break
    
    def hasNext(self):
        return self.nextEle is not None
    
    def next(self):
        if not self.hasNext():
            raise StopIteration("No more element")
        result = self.nextEle
        self.advance()
        return result
    
    def skip(self,val):
        if self.nextEle == val:
            self.advance()
        else:
            self.skipMap[val] = self.skipMap.get(val,0)+1
