# Leetcode problem link : https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator
# Time Complexity:    O(n) for skipping elements in hashmap in worst case
# Space Complexity:   O(n) for hashmap of elements to be skipped
#  Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Your code here along with comments explaining your approach
'''
    Use functionality of inbuilt iterator
    Keep track of elements and count in hashmap for elements to be skipped
    Update cursor for next value
'''  

class SkipIterator:

    def __init__(self, iterator):
        self.iterator = iterator
        self.skipMap = defaultdict(int)
        self.cursor = None
        self.advance()
        

    def hasNext(self) -> bool:
        return self.cursor!= None


    def next(self) -> int:
        value = self.cursor
        self.advance()
        return value
    '''
	The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	'''
    def skip(self, val: int) -> None:
        if self.cursor == val:
            self.advance()
        else:
            self.skipMap[val]+=1


    def advance(self) -> None:
        self.cursor = None
        nextValue = next (self.iterator, None)
        while self.cursor == None and nextValue != None:
            if self.skipMap[nextValue]==0:
                self.cursor = nextValue
                break
            else:
                self.skipMap[nextValue]-=1
            nextValue = next(self.iterator, None)


Time: O(1)
Space: O(Max number of elements to be skipped at time t)

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