"""
Time Complexity -
hashNext - O(1)
next - O(n)
Skip - O(1)
Space Complexity -
hashNext - O(1)
next - O(1)
Skip - O(n)
Using a hashmap to store skipped elements and there count
A pointer that points to index of element
Input Iterator List
hashNext-
Checks if pointer+1 is equal to or has exceeded length of list then return False else return True 
next -
If hasNext element then checks if current pointer loacation element in hashmap if yes then skips it and decrements count by 1 and returns value 
Skip - 
Stores number as key that is to be skipeed with value as count
"""

class SkipIterator:

    def __init__(self,iterator):
        self.hashmap = {}
        self.ptr = -1
        self.iterator = iterator    

    def hasNext(self):
        if self.ptr+1 >= len(self.iterator):
            return False
        else:
            return True    
    
    def next(self):
        if self.hasNext():
            self.ptr +=1
            if self.iterator[self.ptr] in self.hashmap and  self.hashmap[self.iterator[self.ptr]]> 0 :
                self.hashmap[self.iterator[self.ptr]] -=1
                self.next()
            return self.iterator[self.ptr]
        else:
            return None    
    
    def skip(self,num):
        if num in self.hashmap:
            self.hashmap[num] +=1
        else:
            self.hashmap[num] = 1

itr = SkipIterator([2, 3, 5, 6, 5, 7, 5 , -1, 5, 10])
print(itr.hasNext()) # true
print(itr.next()) # returns 2
itr.skip(5)
print(itr.next()) # returns 3
print(itr.next()) # returns 6 because 5 should be skipped
print(itr.next()) # returns 5
itr.skip(5)
itr.skip(5)
print(itr.next()) # returns 7
print(itr.next()) # returns -1
print(itr.next()) # returns 10
print(itr.hasNext()) # false
print(itr.next()) #error 