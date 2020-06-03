#Time complexity:O(n)
#Space complexity:O(n), n is the number of skips pending.
#Doubts in TC and SC.
#Algorithm:
#1. Create queue and a dict for keeping nums and count of #skips encountered for a particular number
#2. In hasNext(), I checking of if the next element exists and does that needs to be skipped by calling advance(). If so I will add it in the count. 
#3. In next (), I check i call hasnext() and return the top element. 
#4. In advance() I check if the next elemnt is to be skipped if yes then I move my next pointer to next element in the nums.

from collections import defaultdict, deque

class SkipIterator:
    def __init__(self, nums):
        self.nums = deque(nums)
        self.cnt = defaultdict(int)

    def hasNext(self):
      self._skip()
      return len(self.nums) > 0
    
    def skip(self, i):
      self.cnt[i] += 1

    def next(self):
      if not self.hasNext():
        return None

      return self.nums.popleft()

    def advance(self):
      while len(self.nums) > 0 and self.nums[0] in self.cnt:
        self.cnt[self.nums[0]] -= 1

        if self.cnt[self.nums[0]] == 0:
          del self.cnt[self.nums[0]]

        self.nums.popleft()
