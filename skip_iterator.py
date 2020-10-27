"""
Problem: Design a Skip Iterator that supports a method skip(int val).
    - When it is called the next element equals val in iterator sequence should be skipped.
Leetcode: https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator
Approach:
    - Use functionality of inbuilt iterator
    - Keep track of elements and count in hashmap for elements to be skipped
    - Update cursor for next value
Time Complexity: O(n) for skipping elements in hashmap in worst case
Space Complexity: O(n) for hashmap of elements to be skipped
Did this code successfully run on Leetcode: Yes
"""


class SkipIterator:
    def __init__(self, iterator):
        self.iterator = iterator
        self.skipMap = {}
        self.cursor = None
        self.__seekCursor()

    def hasNext(self) -> bool:
        return self.cursor != None

    def next(self) -> int:
        element = self.cursor
        self.__seekCursor()
        return element

    def skip(self, val: int) -> None:
        # no need to update count in hashmap if current value is skipped
        if self.cursor == val:
            self.__seekCursor()
        else:
            if val in self.skipMap:
                self.skipMap[val] += 1
            else:
                self.skipMap[val] = 1

    def __seekCursor(self) -> None:
        self.cursor = None
        nextValue = next(self.iterator, None)
        # iterate till either cursor does not contain a valid value or there is no next value to be fetched
        while self.cursor is None and nextValue is not None:
            element = nextValue

            # if element is present and not in skipped map, cursor can be initialized
            if element not in self.skipMap:
                self.cursor = element
                break
            # decrement the count from skip map for the current element as it has been skipped while iterating
            else:
                self.skipMap[element] -= 1
                if self.skipMap[element] == 0:
                    del self.skipMap[element]

            nextValue = next(self.iterator, None)


itr = SkipIterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))
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