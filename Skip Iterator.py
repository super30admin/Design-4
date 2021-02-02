class SkipIterator:
    def __init__(self, it):
        self.itr = iter(it)
        self.map = {}
        self.curr = None
    
    def hasNext(self):
        try:
            while not self.curr:
                temp = next(self.itr)
                if self.map.get(temp, 0) > 0:
                    self.map[temp] -= 1
                else:
                    self.curr = temp
            return True
        except:
            self.curr = 'Error'
            return False
    
    def next(self):
        if self.curr == 'Error':
            raise Exception("Iter object out of bound.")
        if not self.curr:
            self.hasNext()
        temp = self.curr
        self.curr = None
        return temp
    
    def skip(self, k):
        self.map[k] = self.map.get(k, 0) + 1
        return

#Driver Code
itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
print(itr.hasNext()) # true
print(itr.next()) # returns 2
print(itr.skip(5))
print(itr.next()) # returns 3
print(itr.next()) # returns 6 because 5 should be skipped
print(itr.next()) # returns 5
print(itr.skip(5))
print(itr.skip(5))
print(itr.next()) # returns 7
print(itr.next()) # returns -1
print(itr.next()) # returns 10
print(itr.hasNext()) # false
print(itr.next()) # error