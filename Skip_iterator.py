# Time: O(1)
# Space: O(1)


class SkipIterator:

    def __init__(self, iterator):
        #   initialize a global cursor, HashMap to map values to their counts, global iterator
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

    '''
	The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	'''
    def skip(self, val: int) -> None:

        if (self.cursor == val):        #   no need to put count in HashMap if current value
            self.__seekCursor()

        else:                           #   update the count in HashMap
            if (val in self.skipMap):
                self.skipMap[val] += 1
            else:
                self.skipMap[val] = 1

    def __seekCursor(self) -> None:

        #   Time Complexity:    O(n) in the worst case as described above
        #   Space Complexity:   O(n) in the worst case as described above
        self.cursor = None

        nextValue = next(self.iterator, None)             
	# if cursor is not None or if nextValue is None => break the loop
        while(self.cursor == None and  nextValue != None):  
            element = nextValue
	    # if element not in HashMap => assign cursor to that element and break
            if (element not in self.skipMap):               
                self.cursor = element
                break
	  # else update the count, move forward and remove the key if value is 0
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
