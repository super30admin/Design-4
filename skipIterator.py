class SkipIterator:
    def __init__(self, iterator):
        self.iterator = iterator
        self.skip_map = {}
        self.next_element = None
        self._advance()
    
    def _advance(self):
        """Advance the iterator to the next valid element."""
        self.next_element = None
        while True:
            try:
                current = next(self.iterator)
                if current in self.skip_map:
                    self.skip_map[current] -= 1
                    if self.skip_map[current] == 0:
                        del self.skip_map[current]
                else:
                    self.next_element = current
                    break
            except StopIteration:
                break
    
    def has_next(self):
        """Check if there is a next valid element."""
        return self.next_element is not None
    
    def next(self):
        """Get the next element and advance the iterator."""
        if not self.has_next():
            raise StopIteration("No more elements")
        result = self.next_element
        self._advance()
        return result
    
    def skip(self, value):
        """Skip the next occurrence of a value."""
        if self.next_element == value:
            self._advance()
        else:
            self.skip_map[value] = self.skip_map.get(value, 0) + 1


# Example usage
if __name__ == "__main__":
    itr = SkipIterator(iter([5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8]))
    print(itr.has_next())  # True
    itr.skip(5)
    print(itr.next())      # 6
    itr.skip(5)
    print(itr.next())      # 7
    print(itr.next())      # 6
    itr.skip(8)
    itr.skip(9)
    print(itr.next())      # 5
    print(itr.next())      # 5
    print(itr.next())      # 6
    print(itr.has_next())  # True
    print(itr.next())      # 8
    print(itr.has_next())  # False
