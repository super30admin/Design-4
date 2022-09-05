# Time Complexity: Beside each function
# Space: Overall O(n) extra for the hashmap
from collections import defaultdict

class SkipIterator:

    def __init__(self,iterator): # O(1) time and space

        self.h = defaultdict(int)
        self.ptr = -1
        self.iterator = iterator
        
    def hasNext(self): # O(1) time and space
        if self.ptr+1 >= len(self.iterator):
            return False
        else:
            return True
    
    def next(self): # O(n) time and space
        if self.hasNext():
            
            self.ptr +=1
            
            if self.h[self.iterator[self.ptr]]>0:
                self.h[self.iterator[self.ptr]] -=1 # If the element at ptr has a value more than 0 in hashmap then it should be skipped. We reduce the value in hashmap and increase the ptr to skip this element
                self.ptr +=1
                
            return self.iterator[self.ptr]

        else:
            return None
        
    def skip(self,num): # O(1) time and space
        self.h[num] += 1 # if we are asked to skip an element, add the value in the hashmap by 1
        
itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])

print(itr.hasNext()) # true

print(itr.next()) # returns 2

itr.skip(5)

print(itr.next()) # returns 3

print(itr.next()) # returns 6 because 5 should be skipped

print(itr.next()) # returns 5

itr.skip(5)

itr.skip(5)

print(itr.next()) # returns 7

print(itr.next()) # returns -1

print(itr.next()) # returns 10

print(itr.hasNext()) # false

print(itr.next()) #error 