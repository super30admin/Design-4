# Time Complexity : O(N) number of elements in a list
# Space Complexity : O(1)
# Did this code successfully run on Leetcode: Yes
# Any problem you faced while coding this : No
#we will keep a count of elemenst that has to be skipped in hashmap. then we will iterate  through the imput list. When e encounter a skip we will check if current el is the skip el #if yes then we will bypass it if not reduce it count in hashmap and 

from collections import defaultdict
class SkipIterator():

    def __init__(self, nums):

        self.nums = nums
        self.pos = 0
        self.skip_ = defaultdict(lambda : 0)


    def hasNext(self):

        if self.pos < len(self.nums):
            return True      

    def next(self):
        cur = self.pos

        #we check if cur position contains a element that is supposed to be  skipped
        while cur < len(self.nums):
            # if hashmpa has element at currnt position with value > 0, we decrease and move the cursor, and continue the loop
            if self.skip_[self.nums[cur]] > 0:
                self.skip_[self.nums[cur]] -= 1
                cur += 1
                continue
            #If not present in hashmap, break out
            else:
                break
        #move pos to next position       
        self.pos = cur+1    
        return self.nums[cur]

    def skip(self, num):

        self.skip_[num] += 1
'''         
itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
print (itr.hasNext())#; // true
print (itr.skip(2))
print (itr.next())#; // returns 2
print (itr.skip(5))#;
print (itr.next())#; // returns 3
print (itr.next())#; // returns 6 because 5 should be skipped
print (itr.next())#; // returns 5
print (itr.skip(5))#;
print (itr.skip(5))#;
print (itr.next())#; // returns 7
print (itr.next())#; // returns -1
print (itr.hasNext())
print (itr.next())#; // returns 10
#; // false
print (itr.next())#; // error
''' 

class SkipIterator:

    def __init__(self,it):
        self.mapp = {}
        self.advance()
        self.it = it
        self.nextEl = None
        

    # Time = O(1)
    def hasNext(self):
        return self.nextEl != None

    # Time = O(1)
    def next(self): 
        temp = self.nextEl
        self.advance()
        return temp

   
    # Time = O(n) in worst case if all n elements are distinct
    def skip(self,val): 
        if self.nextEl == val:
            self.advance()
        else:
            if val in self.mapp:
                self.mapp[val] += 1
            else:
                self.mapp[val] = 1

    # Time = O(n) in worst case if all n elements are distinct
    def __advance(self):
        self.nextEl = None
        nextVal = self.next(self.it,None)
        while nextEl == None and nextVal != None:
            if self.nextVal not in self.mapp:
                self.nextEl = self.nextVal
                break
            else:
                self.mapp[nextVal] -= 1
                if self.mapp[nextVal] == 0:
                    del self.mapp[nextVal]
            nextVal = self.next(self.it,None)
    
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
                
