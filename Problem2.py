class SkipIterator:
    
    """
    
        Student : Shahreen Shahjahan Psyche
        Time: advance : O(N)
              hasNext : O(1)
              next    : O(N)
              skip    : O(N)
        Space : O(N) [HashMap size]
        Passed All Test Cases : Yes
    
    """
    
    
    def __init__(self, iteratorList):
        
        # Initilizing my data structures
        self.iterator = iteratorList
        self.records = {}
        self.index = 0
    
    # This method skips the values using the hashmap
    def advance(self):
        flag = False
        while not flag and len(self.iterator) > self.index:
            if self.iterator[self.index] in self.records.keys():
                self.records[self.iterator[self.index]] -= 1
                if self.records[self.iterator[self.index]] == 0:
                    del self.records[self.iterator[self.index]]
                self.index += 1
            else:
                flag = True
    
    # checks whether there is any element in next
    def hasNext(self):
        if self.index >= len(self.iterator):
            return False
        return True
    
    # returns the next element
    def next(self):
        self.advance()
        if self.hasNext():
            val = self.iterator[self.index]
            self.index += 1
            return val
            
        else:
            return float('-inf')
        
        """

        * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.

        * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
        
        """
    # skips the value if it is in the current index. Otherwise it saves in the hashmap
    def skip(self, val):
        if self.iterator[self.index] == val:
            self.index += 1
            self.advance()
        else:
            if val not in self.records.keys():
                self.records[val] = 0
            self.records[val] += 1
        
        

        
# driver
        
itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
print(itr.hasNext())
print(itr.next())
itr.skip(5)
print(itr.next())
print(itr.next())
print(itr.next())
itr.skip(5)
itr.skip(5)
print(itr.next())
print(itr.next())
print(itr.next())
print(itr.hasNext())
print(itr.next())
    
    


