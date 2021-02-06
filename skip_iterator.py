# Brute Force Approach: We would maintain a hashhashmap with values that needs to be skipped as keys and values as current times it needs to be skipped
# Keep decrementing the count
# Above wont work when we need to be skipping value at first index

# Approach
# whenever skip value is same as where the next pointer is currently at, we 'advance' to next's next. In the case this is not true, add to hashhashmap

# Time - O(1) 
# Space - O(N) for hashmap -- storing skips 

from collections import defaultdict

class SkipIterator:

    def __init__(self, iterator):
        self.iterator = iterator;
       
        # as described in approach, map stores keys as values to be skipped and values as the number of times we should skip it
        self.hashmap = defaultdict(int);
         
        self.current = 0
    
    
    # check if next value exists
    def hasNext(self):
        return self.current < len(self.iterator);

    
    def next(self):

        # If current element is not in the hashmap then it need not be skipped. 
        # The element at the current index will be returned and move the pointer to next element from current
        
        if(self.iterator[self.current] not in self.hashmap):

            # return val at position
            value = self.it[self.current];

            # moving to next
            self.current += 1;
            return value;
        

        # iterate until there is a next element and 
        # current element is present in hashmap
            # decrement the count in hashmap everytime
        
        while(self.current < len(self.iterator) and self.iterator[self.current] in self.hashmap):

            self.hashmap[self.iterator[self.current]] -= 1;
            
            # once count == 0 ready to pop the element
            if(self.hashmap[self.iterator[self.current]] == 0):
                self.hashmap.pop([self.iterator[self.current]]);
            
            # move to next
            self.current += 1;

        # when all elements are iterated return -1
        if(self.current >= len(self.iterator)):
            return -1;

        value = self.iterator[self.current];
        self.current += 1;
        return value;
    
    # anytime we need to skip a value simply increment its count in hashmap
    def skip(self, val):
        self.hashmap[val] += 1;
    
