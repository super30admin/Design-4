#Q: Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. 
# https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator

# Time Complexity : hasNext-> O(1) and rest O(n) 
# Space Complexity : O(n)
# Did this code successfully run on Leetcode : Not on leetcode
# Any problem you faced while coding this : No

# Your code here along with comments explaining your approach
# Iterator already has the functionality of iterating over elements, hence we only need to figure out whether to return the next element or skip it.
# Decide on using the data structure: HashMap for the following reasons:
# No ordering is requires, We can't keep track of all skips, need to keep track of frequency

from collections import defaultdict
class skipIterator(object):

    def __init__(self, it):
        self.it = it
        self.nextElement = None
        self.hmap = defaultdict(int)
        self.advance()

    def advance(self):
        self.nextElement = None
        curr = next(self.it, None)
        while not self.nextElement and curr:
            if self.hmap[curr]>0:
                self.hmap[curr] -= 1
            else:
                self.nextElement = curr
                break
            curr = next(self.it, None)

    #return true if there's a next element present
    def hasNext(self):
        if self.nextElement != None:
            return True
        return False

    #check if next element is in hashmap
    #if it is, skip the element + decrease count by 1 + if count is zero-> remove num from hashmap + advance the iterator pointer ahead until we are at an element that is not in the hashmap
    def next(self):
        curr = self.nextElement
        self.advance()
        return curr

    #put the num in hashmap with frequency of skips required
    def skip(self, num):
        if num == self.nextElement:
            self.advance()
        else:
            self.hmap[num] += 1

#test
itr = skipIterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))
print(itr.hasNext())   # true
print(itr.next())      # returns 2
itr.skip(5)
print(itr.next())      # returns 3
print(itr.next())      # returns 6 because 5 should be skipped
print(itr.next())      # returns 5
itr.skip(5)
itr.skip(5)
print(itr.next())      # returns 7
print(itr.next())      # returns -1
print(itr.next())      # returns 10
print(itr.hasNext())   # returns false
print(itr.next())      # None


