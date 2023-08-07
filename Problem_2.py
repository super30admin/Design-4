# Time Complexity: O(n)
# Space Complexity: O(n)
# Did this code successfully run on Leetcode: Yes
# Any problem you faced while coding this: No

class SkipIterator:
    def __init__(self, stream):
        self.stream = stream
        self.hashmap = {}
        self.itr = -1

    def hasNext(self):
        return self.itr < len(self.stream) - 1

    def next(self):
        if self.itr < len(self.stream) - 1:
            if self.stream[self.itr + 1] not in self.hashmap.keys() or self hashmap[self.stream[self.itr + 1]] == 0:
                self.itr += 1
                return self.stream[self.itr]
            else:
                val = self.stream[self.itr + 1]
                self.hashmap[val] -= 1
                self.itr += 1
                return self.next()
        
    def skip(self, val):
        if val in self.hashmap.keys():
            self.hashmap[val] += 1
        else:
            self.hashmap[val] = 1