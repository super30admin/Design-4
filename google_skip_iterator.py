# Solution

# // Time Complexity : skip() - O(1), if skipping current element then make it none and come out, else just put to be skipped into
#                      dictionary
#                      hasNext - O(N) if in worst case we might have all the future elements in dicitonary, waiting to be skipped
#                      next - O(N) same as hasNext
# // Space Complexity : skip() - O(1)
#                       hasNext - O(1)
#                       next - O(1)
# // Did this code successfully run on Leetcode : 
# // Any problem you faced while coding this : 


# // Your code here along with comments explaining your approach
# Approach is to just move to next to next number if we are trying to skip the next number. If we are trying to skip some other
# number we can keep it in dictionary, so that when we are trying to get next number, we can check dictionary, only if it is not
# in dictionary we will return it, else we keep moving till we find a number which is not to be skipped

from collections.abc import Iterator
from typing import List

class SkipIterator:
    def __init__(self, it):
        self.it = it
        self.nextEl = None
        self.skipDict = {}
    
    def _advance(self):
        pass
    
    def skip(self, num):
        if self.nextEl == num:
            self.next()
        else:
            if num in self.skipDict:
                self.skipDict[num] += 1
            else:
                self.skipDict[num] = 1
    
    def hasNext(self):
        if self.nextEl != None:
            return True
        self.nextEl = next(self.it,None)
        if self.nextEl == None:
            return False
        else:
            while self.nextEl in self.skipDict:
                self.skipDict[self.nextEl] -= 1
                if self.skipDict[self.nextEl] == 0:
                    del self.skipDict[self.nextEl]
                self.nextEl = next(self.it,None)
            if self.nextEl == None:
                return False
            else:
                return True
    
    def next(self):
        if not self.hasNext():
            return None
        if self.nextEl != None:
            result = self.nextEl
            self.nextEl = None
            return result


class Main:
    @staticmethod
    def main():
        it = SkipIterator(iter([5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8]))
        print(it.hasNext())  # True  # nextEl = 5
        it.skip(5)  # nextEl = 6
        print(it.next())  # 6  # nextEl = 7
        it.skip(5)
        print(it.next())  # 7  # nextEl = 6
        print(it.next())  # 6  # nextEl = 6
        it.skip(8)
        it.skip(9)
        print(it.next())  # 5
        print(it.next())  # 5
        print(it.next())  # 6
        print(it.hasNext())  # True
        # it.skip(8)
        print(it.hasNext())  # False
        print(it.next())  # 8


if __name__ == "__main__":
    Main.main()