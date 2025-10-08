#  Time Complexity : next, hasNext -> O(1), skip -> O(1) on average 
#  Space Complexity : O(k) where k is the number of unique elements to be skipped
#  Did this code successfully run on Leetcode : Yes
#  Three line explanation of solution in plain english : This code defines a SkipIterator that extends a normal iterator by allowing specific values to be skipped dynamically. It uses a skipMap dictionary to track how many times each value should be skipped and an advance() method to find the next valid element. In the main function, various skip() and next() calls demonstrate how the iterator skips specified numbers while iterating through the list.

class SkipIterator:
    def __init__(self, it):
        self.nit = iter(it)
        self.skipMap = {}
        self.nextEl = None
        self.advance()
    
    def advance(self):
        self.nextEl = None
        while True:
            try:
                el = next(self.nit)
                if el not in self.skipMap:
                    self.nextEl = el
                    break
                else:
                    self.skipMap[el] -= 1
                    if self.skipMap[el] == 0:
                        del self.skipMap[el]
                
            except StopIteration:
                break
    
    def hasNext(self):
        return self.nextEl is not None
    
    def next(self):
        el = self.nextEl
        self.advance()
        return el
    
    def skip(self, val):
        if val == self.nextEl:
            self.advance()
        else:
            self.skipMap[val] = self.skipMap.get(val, 0) + 1
    
def main():
    itr = SkipIterator([5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8])
    
    print(itr.hasNext())  # True
    itr.skip(5)
    print(itr.next())     # 6
    itr.skip(5)
    print(itr.next())     # 7
    print(itr.next())     # 6
    itr.skip(8)
    itr.skip(9)
    print(itr.next())     # 5
    print(itr.next())     # 5
    print(itr.next())     # 6
    print(itr.hasNext())  # True
    print(itr.next())     # 8
    print(itr.hasNext())  # False

if __name__ == "__main__":
    main()