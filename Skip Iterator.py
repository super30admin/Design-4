# Time Complexity: O(N) for advance function, O(1) for all other operations
# Space Complexity: O(length of number of skips)

# Code ran on LeetCode
# Maintain a dictionary to count number of times skip was called on an integer. In the advance function, move the pointer to next valid index and store the previous result in a variable, which will be printed upon calling next function

class SkipIterator:
    def __init__(self, iterator):
        self.iterator = iterator
        self.skip_dict = {}
        self.advance()

    def advance(self): # O(N)
        self.next_element = None
        while not self.next_element:
            try:
                self.next_element = next(self.iterator)
                if self.next_element in self.skip_dict:
                    self.skip_dict[self.next_element] -= 1
                    if self.skip_dict[self.next_element] == 0:
                        del self.skip_dict[self.next_element]
                    self.next_element = None
            except StopIteration:
                self.next_element = None

    def hasNext(self): # O(1)
        return self.next_element is not None

    def next(self):
        if not self.hasNext():
            raise StopIteration()
        next_element = self.next_element
        self.advance()
        return next_element

    def skip(self, num):
        if self.next_element == num:
            self.advance()
        else:
            self.skip_dict[num] = self.skip_dict.get(num, 0) + 1

# Example usage:
nums = [2, 3, 5, 6, 5, 7, 5, -1, 5, 10]
it = SkipIterator(iter(nums))

print(it.hasNext())  # True
print(it.next())     # 2
it.skip(5)
print(it.next())     # 6
print(it.next())     # 7
print(it.next())     # -1
print(it.hasNext())  # True
print(it.next())     # 10
print(it.hasNext())  # False
