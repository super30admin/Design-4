# Time Complexity : O(1)
# Space Complexity : O(n), where n is the number of elements in the input iterator

class SkipIterator:
    def __init__(self, iterator):
        self.it = iterator
        self.skip_map = {}
        self.next_el = None
        self.advance()

    def advance(self):
        self.next_el = None
        while self.next_el is None:
            try:
                el = next(self.it)
            except StopIteration:
                break
            if el in self.skip_map:
                self.skip_map[el] -= 1
                if self.skip_map[el] == 0:
                    del self.skip_map[el]
            else:
                self.next_el = el

    def has_next(self):
        return self.next_el is not None

    def next(self):
        if self.next_el is None:
            raise StopIteration()
        result = self.next_el
        self.advance()
        return result

    def skip(self, val):
        if self.next_el == val:
            self.advance()
        else:
            if val in self.skip_map:
                self.skip_map[val] += 1
            else:
                self.skip_map[val] = 1

# Example 1
iterator = iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
skip_itr = SkipIterator(iterator)

print(skip_itr.has_next())  # True
print(skip_itr.next())      # 2
skip_itr.skip(5)
print(skip_itr.next())      # 3
print(skip_itr.next())      # 6 (skips first 5)
print(skip_itr.next())      # 5
skip_itr.skip(5)
skip_itr.skip(5)
print(skip_itr.next())      # 7
print(skip_itr.next())      # -1
print(skip_itr.next())      # 10
print(skip_itr.has_next())  # False
try:
    print(skip_itr.next())  # Error
except StopIteration:
    print("StopIteration")  # Expected output

# Example 2
iterator = iter([1, 2, 3, 4, 5])
skip_itr = SkipIterator(iterator)

print(skip_itr.next())      # 1
skip_itr.skip(2)
print(skip_itr.next())      # 3 (skips 2)
print(skip_itr.has_next())  # True
print(skip_itr.next())      # 4

# Example 3
iterator = iter([1, 2, 2, 3, 4])
skip_itr = SkipIterator(iterator)

skip_itr.skip(2)
print(skip_itr.next())      # 1
print(skip_itr.next())      # 2 (first 2 is skipped)
skip_itr.skip(3)
print(skip_itr.next())      # 4 (skips 3)
print(skip_itr.has_next())  # False