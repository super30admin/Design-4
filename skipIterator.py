"""
Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.


Time Complexity: O(n) where n = number of elements in input array
Space Complexity : O(k) where k = number of elements to be skipped cause it should be stored in dictionary

Approach:
1. Dictionary + pointer

"""

class SkipIterator:

    def __init__(self,iterator):
        #for keeping count of elem to be skipped

        self.h = defaultdict(int)
        self.ptr = -1

        self.iterator = iterator    
        
    def hasNext(self):
        #if ptr crosses the list size
        if self.ptr+1 >= len(self.iterator):
            return False
        else:
            return True    
        
    def next(self):
        #if next is present
        if self.hasNext():
            self.ptr +=1
            #if next is in hashmap to be skipped
            if self.h[self.iterator[self.ptr]]>0:
                self.h[self.iterator[self.ptr]] -=1
                self.ptr +=1
            return self.iterator[self.ptr]
        else:

            return None    
        
    def skip(self,num):
        self.h[num] +=1
        itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])