


class SkipIterator:

    def __init__(self, iterator):
        self.skipMap = {}       #map to store nums to be skipped along with their count
        self.curNum = None      #holds the element to be sent when next() is called
        self.nativeIterator = iterator  #native iterator
        self.advance()          #this method sets the curNum pointer to hold next element to be sent


    #Here we return the num store in curNum pointer. Also when this is returned, curNum is null
    # hence we need to update our curNum by calling advance() function which sets it to next
    # valid available number.
    #Time complexity: O(1)
    #Space complexity: O(1)
    def next(self)-> int:
        val = self.curNum
        self.advance()
        return val

    #Here if our curNum pointer is None it implies that there are no more elements present.
    # Time complexity: O(1)
    # Space complexity: O(1)
    def hasNext(self) -> bool:
        return self.curNum!=None


    #The element to be skipped is added to the skipMap. If the element to be skipped is same
    #as value in curNum pointer then we call advance() function to make the curNum point to
    #next valid number.
    # Time complexity: O(n) - calls advance to get nextNum
    # Space complexity: O(n) - for skipMap
    def skip(self, val: int) -> None:
        if self.curNum==val:
            self.advance()
        else:
            if val in self.skipMap:
                self.skipMap[val]+=1
            else:
                self.skipMap[val] = 1

    #This function gets the next valid number by iterating through the iterator and if the next num
    # is in skipMap then it decrements the skip num's count and again finds the next valid num
    # Time complexity: O(n)
    # Space complexity: O(n)
    def advance(self):
        self.curNum = None
        nextVal = next(self.nativeIterator, None)

        while nextVal!=None and self.curNum==None:

            if nextVal not in self.skipMap:
                self.curNum = nextVal
                break
            else:
                self.skipMap[nextVal]-=1
                if self.skipMap[nextVal]<=0:
                    self.skipMap.pop(nextVal)
                nextVal = next(self.nativeIterator, None)





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