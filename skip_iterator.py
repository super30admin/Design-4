#Time complexity : O(N)
#space complexity : O(N)

class SkipIterator:
    def __init__(self, iterator):
        self.hmap = {}
        self.itr = iterator
        self.nextEl = -1
        self.advance()
    
    def hasNext(self):
        return self.nextEl != None
    
    def next(self):
        result = self.nextEl
        self.advance()
        return result
        
    def skip(self,num):
        if num == self.nextEl: 
            self.advance()
        else: 
            self.hmap[num] = self.hmap.get(num, 0) + 1
            
    def advance(self): 
        self.nextEl = None
        while self.nextEl == None:
            try:
                temp = next(self.itr)
                if temp: 
                    if temp in self.hmap.keys(): 
                        self.hmap[temp] = self.hmap.get(temp) - 1
                        if self.hmap[temp] == 0: 
                            self.hmap.pop(temp)
                    else:
                        self.nextEl = temp
                        return
            except StopIteration: 
                    return
                
if __name__ == '__main__':
    li = [2, 3, 5, 6, 5, 7, 5, -1, 5, 10]
    itr = SkipIterator(iter(li))
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