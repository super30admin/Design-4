---------------------------------Skip Iterator ----------------------------------------

# Time Complexity : O(N) number of elements in a list
# Space Complexity : O(1)
# Did this code successfully run on Leetcode: Yes
# Any problem you faced while coding this : No
# 
# In Skip iterator, a dict can be used to store the skip values and when ever we encountered the value in the dict, we will skip that 
# element and move the next pointer to the next position. The advance should be done when the next pointer is equal to the value of skip element also.
# So we need to include the advance call in skip function also when nextval is equal to the num of skip.



class SkipIterator:
    def __init__(self, it):
        self.nums = it
        self.d = collections.defaultdict()
        self.i = None
        self.advance()

    def hasNext(self) ->bool:
        if self.i != None:
            return True
        else:
            return False

    def next(self):
        ele = self.i
        self.advance()
        return ele

            

    def skip(self, num):
        if self.i == num:
            self.advance()
        else:
            if num in self.d:
                self.d[num]+=1
            else:
                self.d[num] = 1
            
    def advance(self):
        self.i = None
        nextVal = next(self.nums, None)
        while self.i == None and nextVal != None:
            ele = nextVal
            if ele not in self.d:
                self.i = ele
                break
            else:
                self.d[ele] -=1
                if self.d[ele] == 0:
                    del self.d[ele]
            nextVal = next(self.nums, None)
            
            
            
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