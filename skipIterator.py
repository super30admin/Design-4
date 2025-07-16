from collections import defaultdict

class SkipIterator:
    def __init__(self, iterator):
        self.iterator = iter(iterator)
        self.skip_map = defaultdict(int)
        self.next_elem = None
        self._advance()
    
    def _advance(self):
        self.next_elem = None
        while True:
            try:
                el = next(self.iterator)
                if self.skip_map[el] > 0:
                    self.skip_map[el] -= 1
                    if self.skip_map[el] == 0:
                        del self.skip_map[el]
                else:
                    self.next_elem = el
                    break
            except StopIteration:
                break

    def has_next(self):
        return self.next_elem is not None

    def next(self):
        if not self.has_next():
            raise StopIteration("No more elements")
        result = self.next_elem
        self._advance()
        return result

    def skip(self, val):
        if self.next_elem == val:
            self._advance()
        else:
            self.skip_map[val] += 1



itr = SkipIterator([2, 3, 5, 6, 7, 5, -1, 5, 10])

print(itr.has_next())  
print(itr.next())      
itr.skip(5)
print(itr.next())      
print(itr.next())      
print(itr.next())      
itr.skip(5)
itr.skip(5)
print(itr.next())    
print(itr.next())      

print(itr.has_next())  
if itr.has_next():
    print(itr.next())
else:
    print("No more elements")
