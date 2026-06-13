#skip : 1 skip, 2 store (in skip map)
#advance : 1 update nextEl ie self.nextEl = curr, 2 reduce skip count(in skip map)
"""
1) advance()
→ pick next valid number
→ set nextEl

2) skip(x)
if x == nextEl
→ skip current
→ advance immediately
else
→ future skip ie add in skipMap

3) next()
→ return current nextEl
→ call advance()

4) advance() brain:
if picked num in skipMap
→ reduce freq
→ skip it
→ continue moving
else
→ nextEl = picked num
"""
    # nums = [5, 5, 2, 5, 6]

    # init:
    # advance() -> picked 5 -> nextEl = 5

    # skip(5):
    # current nextEl == 5 -> advance() immediately -> picked 5
    # skipMap = {}

    # skip(5):
    # current nextEl == 5
    # advance() immediately -> picked 2
    # skipMap = {}

    # skip(5):
    # current nextEl = 2 != 5
    # future skip needed
    # skipMap = {5:1}

    # next():
    # return 2 -> advance()

    # advance():
    # saw 5 -> skipped using skipMap
    # skipMap {5:1} -> {5:0} -> removed
    # continued -> picked 6
    # nextEl = 6
from collections import defaultdict
class SkipIterator:
    #initialise DSs
    def __init__(self, it):
        self.nums = it  # Store nums: it = iter([5, 5, 2, 5, 6])
        self.i = 0  # Walking pointer
        self.skipMap = {}
        self.nextEl = None
        # Preload first valid element
        self.advance()


    def advance(self):
        nums = self.nums
        skipMap = self.skipMap
        # reset current valid num
        self.nextEl = None
        while i < len(nums):  #1) if curr is in skipmap 2)else
            curr = nums[self.i]
            i += 1
            # case1: curr is not in skipmap: update nextEl
            if skipMap[curr] == 0:
                self.nextEl = curr
                break
            # case2: skip num
            else:
                skipMap[curr] -= 1
                # {5:0} -> {}
                if skipMap[curr] == 0:
                    del skipMap[curr]

                  
    def hasNext(self):
        return self.nextEl is not None

    def next(self):
        toreturn = self.nextEl  # Save/consume current answer
        self.advance()   # Move to next valid element
        return toreturn

    def skip(self, num_to_skip):
        curr = self.nextEl
        if num_to_skip == curr:  #case1: skip
            self.advance()
        # Future skip: nextEl = 2, skip(5) -> {5:1}
        else:  #case2: action ie reduce skip count
            self.skipMap[num_to_skip] += 1
