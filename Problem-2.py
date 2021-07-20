from collections import defaultdict

"""
TC: O(n)
SC: O(n)
"""


class SkipIterator:
    def __init__(self, nums):
        self.next_element = None
        self.iterator = iter(nums)
        self.hash_map = defaultdict(int)
        self.move_forward()

    def has_next(self):
        return self.next_element != None

    def next_(self):
        temp = self.next_element
        self.move_forward()
        return temp

    def skip(self, num):
        if num == self.next_element:
            self.move_forward()
        else:
            self.hash_map[num] += 1

    def move_forward(self):
        self.next_element = None
        next_of_iterator = next(self.iterator, None)
        while not self.next_element and next_of_iterator:  # no iterator has next in py so get next before has and check
            if next_of_iterator not in self.hash_map:
                self.next_element = next_of_iterator
            else:
                self.hash_map[num] -= 1
                if self.hash_map[num] == 0:
                    del self.hash_map[num]
                next_of_iterator = next(self.iterator, None)  # update next


it = SkipIterator(iter([5, 6, 7, 5, 6, 8, 9, 5, 5, 6]))
print(it.has_next())
it.skip(5)
print(it.next_())
it.skip(7)
print(it.next_())
print(it.next_())
print(it.next_())
print(it.next_())
it.skip(1)
it.skip(3)
print(it.has_next())