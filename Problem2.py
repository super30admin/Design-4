#Time complexity will be O(n)
#Space complexity will be O(n)
#No issues faced while coding
#Code ran successfully on leetcode compiler

from typing import Iterator
from collections import defaultdict

class SkipIterator:
    def __init__(self, it: Iterator[int]):
        #We are creating a skipMap dictionary
        self.skipMap = defaultdict(int)
        #Initializing the required valriables and we are calling advance function
        self.nextEl = None
        self.nit = it
        self.advance()

    def advance(self):
        #Initially we will set next element to None
        self.nextEl = None
        while True:
            try:
                #We are going to next element in the iterator
                el = next(self.nit)
                #We will be checking the skipMap
                if el in self.skipMap:
                    self.skipMap[el] -= 1
                    if self.skipMap[el] == 0:
                        del self.skipMap[el]
                #If the element is not there in the skipmap, we will add that to the nextElement
                else:
                    self.nextEl = el
                    break
            #If the skip map is empty this particular expcetion will be raised
            except StopIteration:
                break

    def hasNext(self):
        #This will return either True of False
        return self.nextEl is not None

    def next(self):
        #This calls the advance funtion and will be returning the nextElement value 
        result = self.nextEl
        self.advance()
        return result

    def skip(self, num):
        #This will take care of the skip variables
        if num == self.nextEl:
            self.advance()
        else:
            self.skipMap[num] += 1

it = SkipIterator(iter([5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8]))
print(it.hasNext())  # True
it.skip(5)
print(it.next())  # 6
it.skip(5)
print(it.next())  # 7
print(it.next())  # 6
it.skip(8)
it.skip(9)
print(it.next())  # 5
print(it.next())  # 5
print(it.next())  # 6
print(it.hasNext())  # True
print(it.next())  # 8
print(it.hasNext())  # False
