"""
FAANMG Problem #87 {Medium} 

Google | Skip Iterator


# TC:
has_next: O(n)
next: O(1)
skip: O(1)

https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator

Did this code successfully run on Leetcode : Yes


@name: Rahul Govindkumar_RN27JUL2022
"""

import collections
class SkipIterator:
    def __init__(self, iterator):
        # setiing the default variables
        self.skipMap = collections.defaultdict(int) # to store the elements to be skipped, can handle suplicates
        self.pointer = 0 # to maintain the current element of the iterator
        self.iterator = iterator # using the native iterator to go over the list
        self.seek_next() # to fetch the next element

    # check if the list has a next element
    def hasNext(self):
        return (self.pointer != None)

    # print the next element in the list and also find the next pointing element if present
    def next(self):
        element = self.pointer
        self.seek_next()
        return element

    # makign a skip List to keep track of elements to tbe skipped 
    def skip(self, val):
        # edge case, if the current element is to be skipped, then we fetch the next element and make it current
        if self.pointer == val:
            self.seek_next()
        else:
            
            self.skipMap[val] += 1
            

    # to seek the next element, fetch the next element using native iterator
    def seek_next(self):
        self.pointer = None

        next_val = next(self.iterator, None)

        # move to the next element until we find a non-empty element
        while(self.pointer == None and next_val != None):
            # fetch the element
            element = next_val

            # check if that element is not in the skip element
            if element not in self.skipMap:
               # if not, then make the curr as that element and break
                self.pointer = element
                break
            else:
                # else decrement the count in skipMap
                self.skipMap[element] -= 1
                if self.skipMap[element] == 0:
                    del self.skipMap[element]
            # point next_val to the next element using the iterator
            next_val = next(self.iterator, None)

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