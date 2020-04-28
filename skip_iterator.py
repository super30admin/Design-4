"""
Time - 
    has_next - O(1)
    next - O(1)
Space - O(k) for storing skip elements

Description of Design
Input - iterator

Configuration
- self._next -> used to keep track of the elements in the iterator
- skip - Counter

iterator - is python iterator
    has_next - if there is unprocessed next
        - self._next is not None -> True 
        (First time self._next will be None), so we still check for any elements
        in the iterator using iterator's has_next
            Loop over has_next while it reaches the end
                - get the next element 
                - check if next is not in skip or no count assigned to next in skip
                    - update self._next
                    - return True
                else
                    - decrement skip count for the element
    next - Should return the next element from the iterator using iterator's next 
    Need to handle edge cases primarily
        - If the next is called first time before has next(self.next = None), 
        then return iterator's next
        - Else
            - Fetch the next from self._next set by has_next function

    skip - increment counter for ele 
"""

from collections import Counter
class SkipIterator(Iterator):
    def __init__(self,it):
        self._it = it
        self.next = None
        self._skip = Counter()


    def has_next(self):
        if self._next:
            return True

        while self._it.has_next():
            next = self._it.next()
            if next not in self._skip or self._skip[next]!=0:
                self.next = next
                return True
            else:
                self._skip[next]-=1
        return False

    def next(self):
        if not self.has_next():
            raise Exception("NO elements. Error")
        if self.next:
            next = self._it.next()
            self.next = None
            return next
        return self._it.next()

    def skip(self,num):
        self._skip[num]+=1




itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
itr.hasNext(); # true
itr.next(); # returns 2
itr.skip(5);
itr.next(); # returns 3
itr.next(); # returns 6 because 5 should be skipped
itr.next(); # returns 5
itr.skip(5);
itr.skip(5);
itr.next(); # returns 7
itr.next(); # returns -1
itr.next(); # returns 10
itr.hasNext(); # false
itr.next(); # error