class SkipIterator:

    def __init__(self, itr):
        self.itr = itr
        self.nextEl = 0
        self.skipdict = dict()
        self.advance()

    def advance(self):
        self.nextEl = None
        el = next(self.itr, None)
        while(self.nextEl == None and el != None):
            if(el in self.skipdict):
                self.skipdict[el] -= 1
                if(self.skipdict[el] == 0):
                    del self.skipdict[el]
            else:
                self.nextEl = el
                break
            el = next(self.itr, None)

    def hasnext(self):
        return self.nextEl != None

    def next(self):
        res = self.nextEl
        self.advance()
        return res

    def skip(self, val):
        if(val == self.nextEl):
            self.advance()
        else:
            if(val not in self.skipdict):
                self.skipdict[val] = 0
            self.skipdict[val] += 1


itr = SkipIterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))
print(itr.hasnext())   # true
print(itr.next())      # returns 2
itr.skip(5)
print(itr.next())      # returns 3
print(itr.next())      # returns 6 because 5 should be skipped
print(itr.next())      # returns 5
itr.skip(5)
itr.skip(5)
print(itr.next())      # returns 7
print(itr.next())      # returns -1
print(itr.next())      # returns 10
print(itr.hasnext())   # returns false
print(itr.next())      # None



# itr = SkipIterator(iter([1,2,2,2,3]))
# itr.skip(1)
# print(itr.hasnext())
# itr.skip(2)
# itr.skip(2)
# itr.skip(2)
# print(itr.hasnext())
# print(itr.next())
# # itr.skip(3)
# # print(itr.hasnext())
# # itr.skip(2)
# # itr.skip(2)
# # itr.skip(2)
# # print(itr.next())
# # print(itr.next())



