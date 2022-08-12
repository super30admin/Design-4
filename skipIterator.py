class SkipIterator:
    def __init__(self, iterator):
        # Initialising global variables - nextEl, iterator, hashmap to map element with its skip counts
        self.iterator = iterator
        self.skipMap = {}
        self.nextEl = None
        self.advance()          # To advance nextEl to the next element
        
        
    def advance(self):
        self.nextEl = None
        nextVal = next(self.iterator, None)
        
        while(self.nextEl == None and nextVal != None):
            element = nextVal
            if element not in self.skipMap:
                self.nextEl = element
                break
            else:
                self.skipMap[element] -= 1
                if self.skipMap[element] == 0:
                    del self.skipMap[element]
                    
    def next(self):
        result = self.nextEl
        self.advance()
        return result
    
    
    def hasNext(self):
        return (self.nextEl != None)
    
    
    def skip(self, val):
        if self.nextEl == val:              # No need to put in count hashmap if nextEl is curr val, straight away advance the nextEl
            self.advance()
        else:
            if val in self.skipMap:         # Update the count in hashmap    
                self.skipMap[val] += 1 
            else:
                self.skipMap[val] = 1
        
        
        
itr = SkipIterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))
print(itr.hasNext())   # true
print(itr.next())      # returns 2
itr.skip(5)
print(itr.next())      # returns 3
print(itr.next())      # returns 6 because 5 should be skipped
print(itr.next())      # returns 5
itr.skip(5)
itr.skip(5)
print(itr.next())      # None         
 