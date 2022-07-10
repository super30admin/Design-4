# // Time Complexity :O(1), for next and skip
# // Space Complexity :O(n),hashmap
# // Did this code successfully run on Leetcode :yes
# // Any problem you faced while coding this :no


# // Your code here along with comments explaining your approach






class SkipIiterator:
    def __init__(self):
        li=[5,6,7,5,6,8,9,5,5,6]
        self.li=iter(li)
        self.nextEl=None
        self.map={}
        self.advance()
        
        
    def next(self):
        # print(self.nextEl)
        temp=self.nextEl
        # print(temp)
        self.advance()
        # print(self.nextEl)
        print(temp)
    
    
    def skip(self,num):
        # print(self.nextEl)
        if num==self.nextEl:
            self.advance()
        else:
            if num not in self.map.keys():
                self.map[num]=0
            self.map[num]=self.map[num]+1
        # print(self.map)
    def advance(self):
        #give correct next el
        self.nextEl=None
        while(self.nextEl==None):
            # print("hai")
            el=next(self.li)
            # print(el)
            if el in self.map.keys():
                # print("a")
                x=self.map[el]
                self.map[el]=x-1
                if self.map[el]==0:
                    del self.map[el]
            else:
                self.nextEl=el
        # print("x",self.nextEl)
                

obj=SkipIiterator()
obj.skip(5)
print("x")
obj.next()
print("x")
obj.skip(7)
print("x")
obj.skip(5)
print("x")
obj.skip(8)
print("x")
obj.next()
print("x")
obj.next()
print("x")
obj.next()
obj.next()
obj.skip(1)
obj.skip(3)