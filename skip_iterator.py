#Problem 2: Implement SkipIterator
#time Complexity: O(skips) for next and O(1) has_next and skip
#Space Complexity: O(n), skip_bank
'''
We keep a skip_bank. When we call next,
we return what we're pointing at and move to the 
next valid number (with skip_bank). everytime we skip
a number we decrement it's value in the bank.
As long as we are pointing at something, has_next return true
if we skip a number that is equal to the value we are at now 
we advance and don't do anything else. If it's not, we 
increment the skip bank for that value
'''
class SkipIterator:
    
    def __init__(self,nums):
        self.nums=nums
        self.i=0
        self.skip_bank=collections.defaultdict(int)
    def advance():
        n=self.nums[self.i]
        while self.skip_bank[n] and self.i<len(self.nums):
            skip_bank[n]-=1
            self.i+=1
            n=self.nums[self.i]
    def next():
        to_return=self.nums[self.i]
        self.advance()
        return to_return
        
    def has_next():
        return (self.i<len(self.nums))
    
    def skip(val):
        if val==self.nums[self.i]:self.advance()
        else: self.skip_bank[val]+=1
