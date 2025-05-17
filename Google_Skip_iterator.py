# Time Complexity for hasNext: O(1)
# Time Complexity for next: O(1)
# Time Complexity for skip: O(1)
# Space Complexity for hasNext: O(1)
# Space Complexity for next: O(1)
# Space Complexity for skip: O(n) where n is the number of elements in the skip map
# Did this code run on Leetcode: Yes

# Approach:
# 1. Create a class SkipIterator that takes an iterator as input.
# 2. Create a method advance to get the next element from the iterator.
# 3. Create a method hasNext to check if there are more elements in the iterator.
# 4. Create a method next to return the next element from the iterator.
# 5. Create a method skip to skip the next element in the iterator.
# 6. Use a dictionary to keep track of the elements to be skipped.
# 7. Use a while loop to keep getting the next element until there are no more elements or the next element is not in the skip map.
# 8. Use a try-except block to handle the StopIteration exception when there are no more elements in the iterator.

class SkipIterator:
    def __init__(self, iterator):
        self.iterator = iterator
        self.skip_map = {}
        self.nextEl = None
        self.advance()
        
    def advance(self):
        self.nextEl = None
        while True: 
            try:
                el = next(self.iterator)
                if el in self.skip_map:
                    self.skip_map[el] -= 1
                    if self.skip_map[el] == 0:
                        del self.skip_map[el]
                else:
                    self.nextEl = el
                    break
            except StopIteration:
                break
                    
    def hasNext(self):
        return self.nextEl is not None

    def next(self):
        if not self.hasNext():
            raise StopIteration("No more Elements")
        result = self.nextEl
        self.advance()
        return result

    def skip(self, val):
        if self.nextEl == val:
            self._advance()
        else:
            self.skip_map[val] = self.skip_map.get(val, 0) + 1


def main():
    nums = [2, 3, 5, 6, 5, 7]
    it = SkipIterator(iter(nums))

    print(it.hasNext())  # True
    print(it.next())     # 2
    it.skip(5)           # Skip first 5
    print(it.next())     # 3
    print(it.next())     # 6
    print(it.next())     # 5 (the second 5)
    print(it.hasNext())  # True
    print(it.next())     # 7
    print(it.hasNext())  # False


if __name__ == "__main__":
    main()
