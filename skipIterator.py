class SkipIterator:
    def __init__(self, it):
        self.nextEl = None
        self.skip_map = defaultdict(int)
        self.nit = it
        self.advance()
        
    def advance(self):
        self.nextEl = None
        while True:
            el = next(self.nit, None)
            if not el: # reached the end
                break
            if el in self.skip_map: # if in map either reduce count or remove element if count = 0
                self.skip_map[el] -= 1
                if self.skip_map[el] == 0:
                    del self.skip_map[el]
            else:
                self.nextEl = el
                break
    
    def skip(self, num:int):
        if self.nextEl == num:
            self.advance()
        else:
            if num in self.skip_map:
                self.skip_map[num] += 1
            else:
                self.skip_map[num] = 0

    def __next__(self):
        if not self.nextEl: # reached the end
            raise StopIteration 
        result = self.nextEl
        self.advance()
        return result
    
    def hasnext(self):
        return self.nextEl != None
    
    def iter(self):
        return self



it = SkipIterator(iter([1,2,3,4,5]))

it.skip(1)

it.skip(5)
print(it.hasnext())
print(next(it,None))
print(next(it,None))
print(it.hasnext())
print(next(it,None))


