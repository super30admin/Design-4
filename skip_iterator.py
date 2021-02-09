# Time Complexity : O(n) 
# Space Complexity : O(n) 
# Did this code successfully run on Leetcode : Yes
# Three line explanation of solution in plain english
# I store skipped number and frequency in a hashmap. The has_next function goes forward skips all the skipped elements
# and stops at the next correct element. The next function calls has_next and gives the current element

from collections import defaultdict

class SkipIterator:
    def __init__(self, arr):
        self.iterator = iter(arr)
        self.mapping = defaultdict(lambda: 0)
        try:
            self.current_element = next(self.iterator)
        except StopIteration:
            self.current_element = None

    def has_next(self):
        while self.current_element and self.mapping[self.current_element] > 0:
            try:
                self.current_element = next(self.iterator)
            except StopIteration:
                self.current_element = None

            self.mapping[self.current_element] -= 1

        if self.current_element is not None:
            return True
        else:
            return False

    def next(self):
        self.has_next()
        n = self.current_element 
        try:
            self.current_element = next(self.iterator)
        except StopIteration:
            self.current_element = None
        return n

    def skip(self, val):
        self.mapping[val] += 1


if __name__ == "__main__":
    sit = SkipIterator([1,2,3,4,5])
    #sit.skip(5)
    sit.skip(2)

    print(sit.next())
    print(sit.next())
    while sit.has_next():
        print(sit.next())