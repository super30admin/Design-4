"""
Time Complexity : O(n)
Space Complexity : O(1)
"""

class SkipIterator:

    def __init__(self, iterator):
        #   initialize a global nextEl, HashMap to map values to their counts, global iterator
        self.iterator = iterator
        self.skipMap = {}
        self.nextEl = None
        self.advance()

    def hasNext(self) -> bool:

        return (self.nextEl != None)

    def next(self) -> int:
        element = self.nextEl
        self.advance()
        return element

    def skip(self, val: int) -> None:

        if (self.nextEl == val):        #   no need to put count in HashMap if current value
            self.advance()

        else:                           #   update the count in HashMap
            if (val in self.skipMap):
                self.skipMap[val] += 1
            else:
                self.skipMap[val] = 1

    def advance(self) -> None:
        self.nextEl = None

        nextValue = next(self.iterator, None)               #   initialize the next value

        while(self.nextEl == None and  nextValue != None):  #   if nextEl is not None or if nextValue is None => break the loop
            element = nextValue

            if (element not in self.skipMap):               #   if element not in HashMap => assign nextEl to that element and break
                self.nextEl = element
                break
            else:                                           #   else update the count, move forward and remove the key if value is 0
                self.skipMap[element] -= 1
                if (self.skipMap[element] == 0):
                    del self.skipMap[element]

            nextValue = next(self.iterator, None)           #   update the next value


itr = SkipIterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))
print(itr.hasNext())   # true
print(itr.next())      # returns 2
itr.skip(5)
print(itr.next())      # returns 3
print(itr.next())      # returns 6 because 5 should be skipped
print(itr.next())      # returns 5
itr.skip(5)
itr.skip(5)
print(itr.next())      # None 