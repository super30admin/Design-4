class SkipIterator:
    def __init__(self, nums):
        self.next_el = None
        self.it = iter(nums)
        self.skipmap = {}
        self.advance()
    
    def advance(self):
        # advances iterator to next valid location
        try:
            self.next_el = next(self.it)
            if self.next_el in self.skipmap:
                self.skipmap[self.next_el] -= 1
                if self.skipmap[self.next_el] == 0:
                    del self.skipmap[self.next_el]
                self.advance()
        
        except StopIteration:
            self.next_el = None
            return    
    
    def hasNext(self):
        return self.next_el != None
    
    def next(self):
        temp = self.next_el

        if not temp:
            raise Exception(StopIteration)

        self.advance()
        return temp
    
    def skip(self, num):
        if self.next_el == num:
            self.advance()
            return 
        
        if num in self.skipmap:
            self.skipmap[num] += 1
        else:
            self.skipmap[num] = 1


            
            
itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])            
print(itr.hasNext()) # true
print(itr.next()) # returns 2
itr.skip(5)
print(itr.next()) # returns 3
print(itr.next()) # returns 6 because 5 should be skipped
print(itr.next()) # returns 5
itr.skip(5)
itr.skip(5)
print(itr.next()) # returns 7
print(itr.next()) # returns -1
print(itr.next()) # returns 10
print(itr.hasNext()) # false
print(itr.next()) #error 