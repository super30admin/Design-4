 from collections import defaultdict

class SkipIterator:

    def __init__(self,iterator):

        """

        Constructor

        """

        #for keeping count of elem to be skipped

        self.h = defaultdict(int)

        #pointer before the beginning

        self.ptr = -1

        #initialize with the list

        self.iterator = iterator    def hasNext(self):

        """

        type: None

        rtype: Boolean

        """

        #if ptr crosses the list size

        if self.ptr+1 >= len(self.iterator):

            return False

        else:

            return True    def next(self):

        """

        type: None

        rtype: int

        """

        #if next is present

        if self.hasNext():

            self.ptr +=1

            #if next is in hashmap to be skipped

            if self.h[self.iterator[self.ptr]]>0:

                self.h[self.iterator[self.ptr]] -=1

                self.ptr +=1

            return self.iterator[self.ptr]

        else:

            return None    def skip(self,num):

        """

        type: int

        rtype: None

        """

        self.h[num] +=1itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])

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

# TC : O(n)
# SC : O(1)