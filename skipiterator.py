from collections import defaultdict

class SkipIterator:

    def __init__(self, it):
        self.it = it
        self.num_dict = defaultdict(int)
        self.nextEl = None
        self.advance()

    def hasNext(self):
        return self.nextEl != None
    
    def next(self):
        temp = self.nextEl
        self.advance()
        return temp

    def skip(self, num):
        if self.nextEl == num:
            self.advance()
        else:
            self.num_dict[num] += 1
            
    def advance(self):
        self.nextEl = None
        tempNext = next(self.it, None)
        while not self.nextEl and tempNext:
            if self.num_dict[tempNext] > 0:
                self.num_dict[tempNext] -= 1
            else:
                self.nextEl = tempNext
                break
            tempNext = next(self.it, None)
