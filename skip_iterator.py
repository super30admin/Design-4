#Problem 2: Implement SkipIterator
#time Complexity: for next and has_next O(skips)
#Space Complexity: O(n), n numbers
'''
We keep a skip_bank, if the element is in
skip_bank we go to the next one, until we
it's not or we get an error. for has_next
we create a fake copy of my skip_bank
and check how far we can go from the present
index. If I reach the end without a valid one
(unskipped) I return False
'''
class SkipIterator:

    def __init__(self,nums):
        self.nums=nums
        self.i=0
        self.skip_bank=collections.defaultdict(int)
    def next():
        n=self.nums[self.i+1]
        while skip_bank[n]:
            skip_bank-=1
            n=self.nums[i+1]
        return n
    def has_next():
        curr=self.i
        fake=self.skip_bank.copy()
        while curr<len(self.nums):
            if fake[self.nums[curr]]:
                fake[self.nums[curr]]-=1
                curr+=1
                continue
            return True
        return False
