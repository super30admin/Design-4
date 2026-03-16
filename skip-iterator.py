''' 
Time Complexity: 
next: O(1) amortized, O(n) worst case when we have to skip all elements in the iterator
hasNext: O(1) amortized, O(n) worst case when we have to
skip all elements in the iterator
skip: O(1)

'''

class SkipIterator:

    def __init__(self, it):
        self.it = it
        self.skip_counter = defaultdict(int)
        self.next_element = None
        

    def hasNext(self):  
        if self.next_element != None:
            return True
        try:
            val = next(self.it)
            while val in self.skip_counter:
                self.skip_counter[val]-=1  
                if self.skip_counter[val]==0:
                    del self.skip_counter[val] 
                val = next(self.it)
            self.next_element = val   
        except StopIteration:
            self.next_element = None
        return self.next_element != None

    def next(self):
        if self.next_element is None and not self.hasNext(): 
            raise StopIteration 
        result = self.next_element
        self.next_element = None
        return result
        
    def skip(self, val):
        if self.next_element == val:
            self.next_element = None 
            self.hasNext() 
        else:
            self.skip_counter[val]+=1
        