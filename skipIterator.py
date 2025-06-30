# advance: TC O(1) amortized , SC O(m) where m = number of skipped values
# hasNext: TC O(1), SC O(1)
# next: TC O(1) amortized, SC O(1)
# skip: TC O(1), SC O(m) 
class SkipIterator:

    def __init__(self, iterator):
        self.iterator=iterator
        self.skipMap={}
        self.next_ele=None
        self.advance()

    
    def advance(self):
        self.next_ele=None
        while self.iterator:
            try:
                next_val=next(self.iterator)
                if next_val in self.skipMap:
                    self.skipMap[next_val]-=1
                    if self.skipMap[next_val]==0:
                        del self.skipMap[next_val]
                    continue
                self.next_ele=next_val
                break
            except StopIteration:
                break
    
    def hasNext(self):
        return self.next_ele is not None

    def next(self):
        if not self.hasNext():
            raise StopIteration()
        
        res=self.next_ele
        self.advance()
        return res
    

    def skip(self,val):
        if self.next_ele==val:
            self.advance()
        else:
            self.skipMap[val]=self.skipMap.get(val,0)+1