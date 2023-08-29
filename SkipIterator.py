#TC: O(1)
#SC: O(1)

class SkipIterator:

    def __init__(self, iterator):
        self.iterator = iterator
        self.skipMap = {}
        self.cursor = None
        self.__seekCursor()

    def hasNext(self) -> bool:

        return (self.cursor != None)

    def next(self) -> int:

        element = self.cursor
        self.__seekCursor()
        return element

    def skip(self, val: int) -> None:


        if (self.cursor == val):      
            self.__seekCursor()

        else:                       
            if (val in self.skipMap):
                self.skipMap[val] += 1
            else:
                self.skipMap[val] = 1

    def __seekCursor(self) -> None:

        self.cursor = None

        nextValue = next(self.iterator, None)           

        while(self.cursor == None and  nextValue != None):  
            element = nextValue

            if (element not in self.skipMap):             
                self.cursor = element
                break
            else:                                          
                self.skipMap[element] -= 1
                if (self.skipMap[element] == 0):
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