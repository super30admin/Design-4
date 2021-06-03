import collections
class SkipIterator():
    def __init__(self, elements):
        self.iter = iter(elements)
        self.nextElem = next(self.iter, None)
        self.skipHash = collections.defaultdict(int)

    def skip(self, val):
        if val == self.nextElem:
            self.__advance()
        else:
            
            self.skipHash[val] += 1

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
            print(temp)