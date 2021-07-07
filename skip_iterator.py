from collections import defaultdict, deque


class Iterator:
    pass


class SkipIterator(Iterator):
    """
        https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator
        Time Complexity - O(n)
        Space Complexity - O(n)
            'n' is the number of elements
    """

    def __init__(self, nums):
        self.nums = nums
        self.cur = -1
        self.skip_dic = defaultdict(int)

    def hasNext(self):
        self._advance_cur()
        return self.cur <= (len(self.nums) - 1)

    def next(self):
        self.cur += 1
        if not self.hasNext():
            # raise Exception('Error! No element')
            return None
        return self.nums[self.cur]

    def _advance_cur(self):
        while self.cur <= (len(self.nums) - 1) and self.nums[self.cur] in self.skip_dic:
            self.skip_dic[self.nums[self.cur]] -= 1
            if self.skip_dic[self.nums[self.cur]] == 0:
                self.skip_dic.pop(self.nums[self.cur])
            self.cur += 1

    """
        The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
        This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
    """

    def skip(self, element):
        if self.nums[self.cur + 1] == element:
            self.cur += 1
        else:
            self.skip_dic[element] += 1


if __name__ == '__main__':
    itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
    print(itr.hasNext())  # true
    print(itr.next())  # returns 2
    print(itr.skip(3))
    print(itr.skip(5))
    # print(itr.next())  # returns 3
    print(itr.next())  # returns 6 because 5 should be skipped
    print(itr.next())  # returns 5
    print(itr.skip(5))
    print(itr.skip(5))
    print(itr.next())  # returns 7
    print(itr.next())  # returns - 1
    print(itr.next())  # returns 10
    print(itr.hasNext())  # false
    print(itr.next())  # error
