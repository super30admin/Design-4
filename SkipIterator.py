#Time complexity: O(n)
#Space complexity: O(n)
class SkipIterator:
    def __init__(self, it):
        self.nit = iter(it)
        self.skipMap = {}
        self.next_ele = None
        self.advance()

    def advance(self):
        self.next_ele = None
        #Calling the default function to get next element
        self.nxtVal=next(self.nit , None)
        while self.nxtVal is not None and self.next_ele is None:
            temp_ele = next(self.nit)
            if temp_ele in self.skipMap:
                self.skipMap[temp_ele] -= 1
                if self.skipMap[temp_ele] == 0:
                    del self.skipMap[temp_ele]
            else:
                self.next_ele = temp_ele

    def hasNext(self):
        return self.next_ele is not None

    def next(self):
        result = self.next_ele
        # Before returning the next element, move the cursor by calling advance method
        self.advance()
        return result

    def skip(self, val):
        if self.next_ele == val:
            self.next_ele = None
            self.advance()
        else:
            if val in self.skipMap:
                self.skipMap[val] += 1
            else:
                self.skipMap[val] = 1


itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
print(itr.hasNext())  # true
print(itr.next())  # returns 2
print(itr.skip(5))
print(itr.next())  # returns 3
print(itr.next())  # returns 6 because 5 should be skipped
print(itr.next())  # returns 5
print(itr.skip(5))
print(itr.skip(5))
print(itr.next())  # returns 7
print(itr.next())  # returns -1
print(itr.next())  # returns 10
print(itr.hasNext())  # false
