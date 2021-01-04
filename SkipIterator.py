#Time complexity O(N)
#Space Complexity 0(k) where k elements to skip in Hashmap
#https://leetcode.com/playground/B8zLCvHL

from collections import defaultdict 
class SkipIterator:
    def __init__(self,list1):
        self.it= iter(list1)
        self.nextEl = next(self.it)
        self.skip_map=defaultdict(int)
    
    def next(self) -> int:
        temp=self.nextEl
        self.advance()
        return temp
        
    
    def hasNext(self) -> bool:
        if self.nextEl:
            return True 
        return False
    
    def skip(self,val):
        #print("skip")
        #print(val)
        #print(self.nextEl)
        if(val == self.nextEl):
            self.advance()
        else:
            self.skip_map[val]+=1
        #print(self.skip_map)
        
    def advance(self):
        self.nextEl=None
        val=next(self.it,None)
        #print("val in advance",val)
        while(self.nextEl==None and val!=None):
            if val in self.skip_map: 
                self.skip_map[val]-=1
                if self.skip_map[val]==0:
                    del self.skip_map[val]
                self.nextEl=next(self.it)
            else:
                self.nextEl=val

# Your NestedIterator object will be instantiated and called as such:
list1=[2,3,5,6,7,4,3,5,1]
it = SkipIterator(list1)
print(it.hasNext())#true
print(it.next())#2
print(it.next())#3
it.skip(5)
print(it.next())#6
it.skip(5)
print(it.next())#7
print(it.next())#4
print(it.next())#3
print(it.next())#1
print(it.hasNext())
            
        
        
