# Space Complexity : O(n) in worst case 
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
# Your code here along with comments explaining your approach
class SkipIterator:

    def __init__(self,it):
        self.it = it
        self.map = {}
        self.nextEl = None 
        self.__advance()
                
    # Time = O(1)
    def hasNext(self):
        return self.nextEl != None 
    
    # Time = O(1)
    def next(self): # returns an next int 
        element = self.nextEl
        self.__advance() # self.nextEl variable will get upadated with next integer
        return element 
     
    """
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should 
        be skipped.
    """
    # Time = O(n) in worst case if all n elements are distinct
    def skip(self,val):  # this is where we will add the element to our self.map dict.
        if val == self.nextEl:
            self.__advance()
        else:
            if val in self.map:
                self.map[val] += 1 
            else:
                self.map[val] = 1 

    # Time = O(n) in worst case if all n elements are distinct
    def __advance(self):
        self.nextEl = None 
        nextValue = next(self.it, None)
        while self.nextEl == None and nextValue != None :
            if nextValue not in self.map:
                self.nextEl = nextValue 
                break 
            else:
                self.map[nextValue] -= 1 
                if self.map[nextValue] == 0:
                    del self.map[nextValue] # because we want to skip only once, otherwise we would skip even if value is 0 
            
            nextValue = next(self.it, None)

    
if __name__ =="__main__":
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