'''
Time Complexity : O(n)#worst case all are skipped
Space Complexity : O(n) #worst case all are skipped

'''

import collections

class Solution:

    def __init__(self, it):
        self.itr = iter(it)
        self.futureNext = None
        self.hashMapSkip = collections.defaultdict(int)
        self.setFuture()

    def hasNext(self): #boolean
        if self.futureNext:
            return True
        return False

    def next(self):
        curr = self.futureNext
        self.futureNext = None
        #setting future next
        self.setFuture()
        return curr


    def skip(self, val):
        if val == self.futureNext:
            self.futureNext = None
            self.setFuture()
        else:
            self.hashMapSkip[val] += 1

    def setFuture(self):
         while self.futureNext == None or self.hasNext():
            elem = next(self.itr, None)
            if elem in self.hashMapSkip:
                if self.hashMapSkip[elem] ==1:
                   self.hashMapSkip.pop(elem)
                else:
                   self.hashMapSkip[elem] -= 1
            else:
                self.futureNext = elem
                break


           
itr = Solution([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
print(itr.hasNext())
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