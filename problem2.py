class SkipIterator:
    def __init__(self, it):
        print("y")
        self._it = it
        self._skip = collections.Counter()
        self._next = next(self._it)

    def has_next(self):
        return self._next!=None
    

    def enext(self):
        if self.has_next()==False:
            print("empty")
            return
        cur=self._next
        self.helper()
        return cur
        
    

  
    def skip(self, num):
        if num==self._next:
            self.helper()
        else:
            if num in self._skip:
                self._skip[num]+=1
            else:
                self._skip[num]=1
       
    def helper(self):
        self._next=None
        
        while(self._next==None ):
            try:
                ele=next(self._it)
                
                if ele!=None:
                    if ele in self._skip:
                        self._skip[ele]-=1
                        if self._skip[ele]==0:
                            del self._skip[ele]
                    else:
                        self._next=ele
                        break
        
                        
            except:
                
                break
        
mylist = [2, 3, 5, 6, 5, 7, 5, -1, 5, 10]
myiterator = iter(mylist)
itr = SkipIterator(myiterator);
print(itr.has_next()) #true
print(itr.enext())#returns 2
print(itr.skip(5))
print(itr.enext())# returns 3
print(itr.enext())# returns 6 because 5 should be skipped
print(itr.enext())# returns 5
print(itr.skip(5))#
print(itr.skip(5))#
print(itr.enext())# returns 7
print(itr.enext())# returns -1
print(itr.enext())# returns 10
print(itr.has_next())# false
print(itr.enext())# error
