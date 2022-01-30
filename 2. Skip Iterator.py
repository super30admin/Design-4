from collections import defaultdict, deque
# Time Complexity - O(n)
# Space Complexity - O(n)
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

    def _skip(self):
        while len(self.nums) > 0 and self.nums[0] in self.cnt:
            self.cnt[self.nums[0]] -= 1

            if self.cnt[self.nums[0]] == 0:
                del self.cnt[self.nums[0]]

            self.nums.popleft()

itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
print(itr.hasNext())
print(itr.next())
print(itr.skip(5))
print(itr.next())
print(itr.next())
print(itr.next())
print(itr.skip(5))
print(itr.skip(5))
print(itr.next())
print(itr.next())
print(itr.next())
print(itr.hasNext())
print(itr.next())