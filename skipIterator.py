#Average case complexity: O(1) per operation, assuming few skips.
#Worst case: O(N) if skipping all elements.
#Space = O(S), where S = number of elements in skipmap
def __init__(self,it):
        self.nit = it
        self.skipmap={}
        self.nextEl = None
        self.advance()
       
def advance(self):
    self.nextEl = None
    while True:
        el = next(self.nit, None)
        if el is None:
            break
        if el in self.skipmap:
            self.skipmap[el] -= 1
            if self.skipmap[el] == 0:
                del self.skipmap[el]
        else:
            self.nextEl = el
            break
def skip(self, num:int):
    if num != self.nextEl:
        if num not in self.skipmap:
            self.skipmap[num]=0
        self.skipmap[num]+=1
    else:
        self.advance()
    
    
def hasnext(self):
    return self.nextEl != None
    

def __next__(self):
    if self.nextEl is None:
        raise StopIteration
    result = self.nextEl
    self.advance()
    return result

def __iter__(self):
    return self