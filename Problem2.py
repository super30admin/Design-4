#Time Complexity :O(n)
#Space Complexity :O(n)  
#Did this code successfully run on Leetcode : Yes
#Any problem you faced while coding this : No

class SkipIterator:
  def __init__(self, stream):
    self.nextEl=0
    self.skipMap={}
    self.nativeIt=-1
    self.stream=stream
    self.advance()
     
  def advance(self):
    self.nextEl=None
    while self.nativeIt<len(self.stream)-1 and self.nextEl==None:
        it=self.stream[self.nativeIt+1]
        if it in self.skipMap.keys():
            self.skipMap[it]-=1
            if self.skipMap[it]<=0:
                del(self.skipMap[it])
            self.nativeIt+=1
        else:
            self.nextEl=it
            self.nativeIt+=1
    
  def hasNext(self):
    return self.nextEl!=None

  def next(self):
    result=self.nextEl
    self.advance()
    return result    
        
  def skip(self, val):
    if val==self.nextEl:
        self.advance()
    else:
        if val not in self.skipMap.keys():
            self.skipMap[val]=0
        self.skipMap[val]+=1
    


it = SkipIterator([5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8])
print(it.hasNext())  # returns true
it.skip(5)
print(it.next())  #returns 6
it.skip(5)
print(it.next())  # returns 7
print(it.next())  # returns 6
it.skip(8)  # skips 8
it.skip(9)  # skips 9
print(it.next())  # returns 5
print(it.next())  # returns 5
print(it.next())  # returns 6
print(it.hasNext())  #returns true
print(it.next())  #returns 8
print(it.hasNext())  # returns False