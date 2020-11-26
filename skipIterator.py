#Difficulties: How to check hasNext method in normal native tierator in python? 
#Time Complexity: O(1)
#space complexity: O(number of elements to be skipped) 
class skipIterator:
    def __init__(self,it):
        self.map = dict()
        self.it = it
        self.nextEl = None
        self.advance()
    
    def hasNext(self):
        return self.nextEl!=None

    def next(self):
        temp = self.nextEl
        self.advance()
        return temp

    def skip(self,elem):
        if elem == self.nextEl:
            self.advance()
        else:
            if elem not in self.map:
                self.map[elem] = 1
            else:
                self.map[elem] += 1

    def advance(self):
        self.nextEl = None
        while self.nextEl is not None and next(self.it):
            el = next(self.it)
            if el in self.map:
                if self.map[el] > 0:
                    self.map[el] -= 1
                elif self.map[el] == 0:
                    del self.map[el]
            else:
                self.nextEl = el
    
li  = [1,2,3,4]
it = iter(li)
skip = skipIterator(it)
skip.next()
skip.skip(3)
    

    
