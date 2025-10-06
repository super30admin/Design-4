'''
Solution: Using native iterator's next() + HashMap for skipping elements
    - When next() is called on skip-iterator, we store the current element
      and advance to next element. Then we check if this element is to be
      skipped using skipMap. 
    - When skip() is called, we first check if the current element is the
      one that needs to be skipped. If Yes, iterator advances, else
      we store the element to be skipped in the skipMap along with its
      frquency.
Time Complexity:
    - next(), skip() - amortized O(1) for all function calls.
    - hasNext() - O(1)
Space Complexity:
    - O(k) - k = number of elements to be skipped.
'''

class outofbounds(Exception):
        pass
class SkipIterator():
    def __init__(self,it):
        self.skipMap = {} # {element to be skipped: frequency}
        self.it = it
        self.current_element = 0
        self.__advance() 

    def __advance(self):
        self.current_element = next(self.it,None) # store the current element and move to next
        # check if this element is to be skipped
        while self.current_element is not None and self.current_element in self.skipMap:
            self.skipMap[self.current_element]-=1 # reduce the frequency
            
            if self.skipMap[self.current_element]==0:
                self.skipMap.pop(self.current_element) # remove the key if done skipping
            
            self.current_element = next(self.it,None)
        
    def hasNext(self): 
        if self.current_element is None: # if iterator is out-of-bounds
            return False
        else:
            return True
    
    def next(self):
        if (self.hasNext()==False):
            raise outofbounds("Error:Iterator has gone out of bounds")
        next_element = self.current_element
        self.__advance()
        
        return next_element

    def skip(self,val):
        '''
        The input parameter is an int, indicating that the next element equals 'val' 
        needs to be skipped.
        This method can be called multiple times in a row. 
        skip(5), skip(5) means that the next two 5s should be skipped.
        '''
        # check if the current element is the one to be skipped
        if self.current_element is not None and self.current_element==val:
            self.__advance()
        else:
            self.skipMap[val] = self.skipMap.get(val,0)
            self.skipMap[val]+=1


itr = SkipIterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))
print(itr.hasNext()) # true
print(itr.next()) # returns 2
print(itr.skip(5)) 
print(itr.next()) # returns 3
print(itr.next()) # returns 6 because 5 should be skipped
print(itr.next()) # returns 5
print(itr.skip(5))
print(itr.skip(5))
print(itr.next()) # returns 7
print(itr.next()) # returns -1
print(itr.next()) # returns 10
print(itr.hasNext()) # false
print(itr.next()) # error