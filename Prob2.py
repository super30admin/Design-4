class SkipIterator(object):
    def __init__(self,it):
       self.it = it
       self.nextEl = None
       self.skipMap = {}
    def hasNext(self):
        return self.nextEl is not None
    def next(self):
        temp = self.nextEl
        self.forward()
        print(temp)
        return temp
    def forward(self):
        self.nextEl = None
        while self.nextEl is None:
            newNum = next(self.it)
            if newNum in self.skipMap:
                self.skipMap[newNum] = self.skipMap[newNum] - 1
                if self.skipMap[newNum] == 0:
                    del self.skipMap[newNum]
            else:
                self.nextEl = newNum
    def skip(self,val):
        if val == self.nextEl:
            self.forward()
        else:
            self.skipMap[val] = self.skipMap.get(val,0)+1

basic_itr = iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
itr = SkipIterator(basic_itr)
itr.hasNext()# true
itr.next()# returns 2
itr.skip(5)
itr.next()# returns 3
itr.next()# returns 6 because 5 should be skipped
itr.next()# returns 5
itr.skip(5)
itr.skip(5)
itr.next() # returns 7
itr.next() # returns -1
itr.next() # returns 10
itr.hasNext() # false
# itr.next() #error
