from collections import defaultdict
from typing import Iterator

class SkipIterator:
    def __init__(self, iterator: Iterator[int]):
        self.it = iterator
        self.skipmap = defaultdict(int)
        self.nextEl = None
        self.advance()
        
    def advance(self):
        self.nextEl = None
        while self.nextEl is None and self.it:
            try:
                curr = next(self.it)
                if self.skipmap[curr] > 0:
                    self.skipmap[curr] -= 1
                    if self.skipmap[curr] == 0:
                        del self.skipmap[curr]
                else:
                    self.nextEl = curr
            except StopIteration:
                break
    
    def hasNext(self):
        return self.nextEl is not None
    
    def next(self) -> int:
        if not self.nextEl:
            raise Exception("no elements")
        temp = self.nextEl
        self.advance()
        return temp
    
    def skip(self, num: int):
        if self.nextEl == num:
            self.advance
        else:
            self.skipmap[num] += 1

            
it = iter([5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8])  # Create an iterator
sit = SkipIterator(it)  # Initialize SkipIterator

print(sit.hasNext())  # True
print(sit.next())  # 5
sit.skip(5)  # Skip 5
print(sit.next())  # 6
print(sit.next())  # 7
sit.skip(7)  # Skip 7
sit.skip(9)  # Skip 9
print(sit.next())  # 6
print(sit.next())  # 8
print(sit.next())  # 5
sit.skip(8)  # Skip 8
sit.skip(5)  # Skip 5
print(sit.hasNext())  # True
print(sit.next())  # 6
print(sit.hasNext())  # False
        