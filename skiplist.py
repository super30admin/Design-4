# Time Complexity : Add - O(1)
# Space Complexity :O(n)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
'''
1. To able to skip a element we need to store all pending skip elements. skip can be called on same elements on multiple times. So we maintain a hashmap of all element to be skipped and frequency, and as we encounter a element to be  skipped we bypass it and reduce the frequency count in hashmap


'''

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