class SkipIterator():
    def __init__(self, elements):
        self.iter = iter(elements)
        self.nextElem = next(self.iter, None)
        self.skipHash = dict()
        
    def skip(self, val):
        if val == self.nextElem:
            self.__advance()
        else:
            # add the element to the skip hash if the element is the same as the current element
            self.skipHash[val] = self.skipHash.get(val, 0) + 1
    
    def hasNext(self):
        print(self.nextElem is not None) 
    
    def __advance(self):
        self.nextElem = None
        iterElem = next(self.iter, None)
        
        while self.nextElem is None and iterElem is not None:
            if iterElem in self.skipHash:
                self.skipHash[iterElem] -= 1
                if self.skipHash[iterElem] == 0:
                    del self.skipHash[iterElem] 
                iterElem = next(self.iter, None)
            else:
                self.nextElem = iterElem
    
    def next(self):
        if self.nextElem is not None:
            temp = self.nextElem
            self.__advance()
            print(temp)  # return the current element


itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
itr.hasNext() # true
itr.next() # returns 2
itr.skip(5)
itr.next() # returns 3
itr.next() # returns 6 because 5 should be skipped
itr.next() # returns 5
itr.skip(5)
itr.skip(5)
itr.next() # returns 7
itr.next() # returns -1
itr.next() # returns 10
itr.hasNext() # false
itr.next() # error