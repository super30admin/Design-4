
'''
Time: hasnext - O(1), skip and next - O(n)
Space: O(n)
'''

class SkipIterator:
    
    def __init__(self, li):
        self.di = dict()
        self.li_itr = iter(li)
        self.next_ele = next(self.li_itr, None)
        
    
    def hasNext(self):
        return self.next_ele != 0
    
    def next(self):
        temp = None
        while self.next_ele != None:
            if self.next_ele in self.di:
                self.di[self.next_ele] -= 1
                if self.di[self.next_ele] == 0:
                    del self.di[self.next_ele]
                self.next_ele = next(self.li_itr, None)
            else:
                temp = self.next_ele
                self.next_ele = next(self.li_itr, None)
                break
        return temp
    
    def skip(self, val):
        if val == self.next_ele:
            self.next_ele = next(self.li_itr, None)
        else:
            if val not in self.di:
                self.di[val] = 0
            self.di[val] += 1
            
cl = SkipIterator([5, 6, 7, 5, 6, 8, 9, 5, 5, 6])

print(cl.hasNext())
print(cl.next())
cl.skip(6)
cl.skip(5)
print(cl.next())
print(cl.next())