# Problem: Design a skip iterator that skips elements based on a given condition.
# The iterator should be able to skip elements that have been marked for skipping.
# The iterator should also be able to check if there are more elements to iterate over.
# The iterator should be able to return the next element in the iteration.

# Approach:
# 1. Create a class SkipIterator that implements the Iterator interface.
# 2. The class should have a constructor that takes an iterable as input and initializes the iterator.
# 3. We handle the skipping logic in the advance method.
# 4. The advance method should check if the next element is in the skip map and skip it if necessary.
# 5. The hasNext method should check if there are more elements to iterate over.
# 6. The __next__ method should return the next element in the iteration.
# 7. The skip method should mark an element for skipping.
# 8. The skip method should also check if the element to be skipped is the current element and advance the iterator if necessary.

from collections.abc import Iterator
class SkipIterator(Iterator):
    # Constructor to initialize the iterator with an iterable
    # The constructor initializes the skip map and the iterator
    # The advance method is called to set the next element to be returned
    def __init__(self, it):
        self.skipMap = {}
        self.nit = iter(it)
        self.advance()
    # The advance method is used to get the next element in the iteration
    def advance(self):
        # Get the next element from the native iterator
        self.nextEl = next(self.nit, None)
        # Do this until we find an element that is not in the skip map or until we reach the end of the iterator
        while self.nextEl is not None:
            # Check if the next element is in the skip map
            if self.nextEl in self.skipMap:
                # If it is, decrement the count in the skip map
                self.skipMap[self.nextEl] -= 1
                # If the count reaches 0, remove it from the skip map
                if self.skipMap[self.nextEl] == 0:
                    del self.skipMap[self.nextEl]
            # If the next element is not in the skip map, we have found the next element to return
            else:
                break
    # The hasNext method checks if the next element exists
    # If the next element is not None, it means there are more elements to iterate over
    def hasNext(self):
        return self.nextEl != None
    # The __next__ method returns the next element in the iteration
    def __next__(self):
        # Store the current next element in a temporary variable
        tmp = self.nextEl
        # Call the advance method to get the next element before we return the current one
        self.advance()
        # Return the temporary variable
        return tmp
    # The skip method marks an element for skipping
    # If the element to be skipped is the current element, we call the advance method to skip it
    def skip(self, num):
        if num == self.nextEl:
            self.advance()
        else:
            self.skipMap[num] = 1 + self.skipMap.get(num, 0)

# Example usage            
itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
print(itr.hasNext())
print(itr.__next__())
print(itr.skip(3))
print(itr.__next__())