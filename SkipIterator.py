class SkipIterator:
    # constructor
    #hasnext
    #next
    #skip
    
    def __init__(self, iterator):
        #We need to maintain the hashmap of skip values.
        self.skipmap=dict()
        #We also need integer for next element pointer
        self.nextElement=None
        #Native Iterator
        self.iterator=iterator
        #Call for the first time, else it will give False for the first value
        self.__moveahead()
        
    def hasNext(self): #O(1)
        return self.nextElement is not None
    
    def next(self): #O(1)
        result=self.nextElement
        try:
            self.__moveahead()
        except:
            return result
        return result
    
    def skip(self, val): #O(1)
        if self.nextElement!=val:
            if val not in self.skipmap:
                self.skipmap[val]=1
            else:
                self.skipmap[val]+=1
            #print(self.skipmap)
        else:
            self.__moveahead()
            
    def __moveahead(self):
        self.nextElement=None
        nextVal=self.iterator.next()
        while self.nextElement is None and nextVal is not None:
            el=nextVal
            if el in self.skipmap:
                self.skipmap[el]-=1
                if self.skipmap[el] ==0:
                    self.skipmap.pop(el)
            else:
                self.nextElement=el
                break
            nextVal= self.iterator.next()
            
                
itr = SkipIterator(iter([5,6,7,5,6,8,9,5,5,6,8]))
print(itr.hasNext())   # true
print(itr.next())      # returns 5
itr.skip(5)
print(itr.next())      # returns 6
print(itr.next())      # returns 7 
print(itr.next())      # returns 6 because 5 should be skipped
itr.skip(5)
itr.skip(5)
print(itr.next())      # returns 8
print(itr.next())      # returns 9
print(itr.next())      # returns 6
print(itr.next())      # return 8
print(itr.hasNext())   # returns false

        
    
