# Time Complexity : hasNext - O(1); next, skip - O(n)
# Space Complexity : O(1) - user level; O(n) - for dictionary in constructor.
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
#
#
# Your code here along with comments explaining your approach

# building my own NativeIterator object and giving it as input to the skipIterator
# since I am sending a data-structure to the skipIterator object, any changes in the input list will reflect in
# skipIterator also --> a ds in a ds is a reference.
from collections import deque


class NativeIterator:
    # creating a deque of the lst of integers to have O(1) while doing next operations.
    def __init__(self, lst: list[int]):
        self.lst = deque(lst)

    def hasNext(self):
        return len(self.lst) > 0

    def next(self):
        if self.lst:
            return self.lst.popleft()


class skipIterator:
    # making given NativeIterator global
    # creating a hashmap to have a count of skip integers
    def __init__(self, it: [NativeIterator]):
        self.it = it
        self.dictu = {}
        self.result = self.it.next()   # to store the result value, initially it is at 0th index of the NativeIterator.

    def advance(self):
        # makes the result None, when ever it comes to advance() function, we store result in a variable.
        self.result = None
        # getting the next valid pointer by checking the given conditions and storing it to the result.
        while self.result is None and self.it.hasNext():  # loop breaks whenever there is a value to the result.
            nextValue = self.it.next()
            # if the nextValue is not in the dictionary, which means we are good to print that value.
            # else, decrease its count and delete it if its count becomes 0.
            if nextValue not in self.dictu:
                self.result = nextValue
            else:
                self.dictu[nextValue] -= 1
                if self.dictu[nextValue] == 0:
                    del self.dictu[nextValue]

    def hasNext(self) -> bool:  # returns True if there is any value in result
        return self.result is not None

    def next(self) -> int:
        # stores the result in a variable and calls the advance() function to get the next pointer of the result
        result = self.result
        self.advance()
        return result

    def skip(self, element):
        # if the present element is the result, we don't want to print it.
        # so, we simply call the advance() function.
        # else, if it is not the current element, which means it might come in the future, we need to skip that value.
        # so store in the hashmap.
        if self.result == element:
            self.advance()
        else:
            if element in self.dictu:
                self.dictu[element] += 1
            else:
                self.dictu[element] = 1


lst = [5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8]
check = skipIterator(NativeIterator(lst))
print(check.hasNext())
print(check.next())
check.skip(6)
print(check.next())
print(check.next())
check.skip(6)
check.skip(9)
print(check.next())
print(check.next())
print(check.next())
print(check.next())
print(check.hasNext())


# not an iterator, since we cannot handle dynamic ability.
# class skipIterator:
#     def __init__(self, it):
#         self.it = it
#         self.dictu = {}
#         self.result = next(self.it, None)
#
#     def advance(self):
#         self.result = None
#         nextValue = next(self.it, None)
#         while self.result is None and nextValue:
#             if nextValue in self.dictu:
#                 self.dictu[nextValue] -= 1
#                 if self.dictu[nextValue] == 0:
#                     del self.dictu[nextValue]
#                     nextValue = next(self.it, None)
#             elif nextValue not in self.dictu:
#                 self.result = nextValue
#
#     def hasNext(self):
#         return self.result is not None
#
#     def next(self):
#         result = self.result
#         self.advance()
#         return result
#
#     def skip(self, element):
#         if element == self.result:
#             self.advance()
#         else:
#             if element in self.dictu:
#                 self.dictu[element] += 1
#             else:
#                 self.dictu[element] = 1
#
#
# lst = [5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8]
# check = skipIterator(iter(lst))
# print(check.hasNext())
# print(check.next())
# check.skip(6)
# print(check.next())
# print(check.next())
# check.skip(6)
# check.skip(9)
# print(check.next())
# print(check.next())
# print(check.next())
# print(check.next())
# print(check.hasNext())
