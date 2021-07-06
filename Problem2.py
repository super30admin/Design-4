"""
// Time Complexity : o(1)
// Space Complexity : o(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

"""
from collections import defaultdict

class SkipIterator:

    def __init__(self,iterator):
        self.map = defaultdict(int) #keeps count of values to skipped
        self.ptr = -1 #pointer that points at the location of next
        self.it = iterator    
        
    def hasNext(self):
        if self.ptr+1 >= len(self.it): #if the list has exhausted
            return False
        else:
            return True
        
    def next(self):
        if self.hasNext():
            self.ptr += 1 #move pointer to next value

            if self.map[self.it[self.ptr]] > 0: #check if it is to be skipped
                self.map[self.it[self.ptr]] -= 1 #if yes, decrement the count
                self.ptr += 1 #and move the pointer
                 
            return self.it[self.ptr] #return value at pointer
        
        else:
            return None    
        
    def skip(self,num):
        self.map[num] += 1 #increment count
        
        
        
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