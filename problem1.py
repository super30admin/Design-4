
class SkipIterator:
  def __init__(self, iterator):
    self.iterator = iterator
    self.skip = {}
    self.cursor = None
    self.seekCursor() # holds value which would be returned in next subsequent call

  def hasNext(self):
    return self.cursor!=None

 #Time Complexity:    O(1)
 #Time Complexity:    O(1)
  def next(self):
    element = self.cursor
    self.seekCursor()
    return element

  #Time Complexity:    O(n)
  #Space Complexity:   O(n)

  def Skip(self, num):
    if self.cursor == num:
        self.seekCursor()
    else:
        if num not in self.skip.keys():
            self.skip[num] = 1
        else:
            self.skip[num] = self.skip[num]+1

  #Time Complexity:    O(n)
  #Space Complexity:   O(n)
  def seekCursor(self):
    self.cursor = None
    hasNext = next(self.iterator,None)
    while self.cursor == None and hasNext != None:
        element = hasNext
        if element not in self.skip:
            self.cursor = element
            break
        else:
            self.skip[element] -=1
            if self.skip[element] == 0:
                del self.skip[element]
        hasNext = next(self.iterator,None)

itr = SkipIterator(iter([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]))  # type: SkipIterator
print(itr.hasNext())   # true
print(itr.next())      # returns 2
itr.Skip(5)
print(itr.next())      # returns 3
print(itr.next())      # returns 6 because 5 should be skipped
print(itr.next())      # returns 5
itr.Skip(5)
itr.Skip(5)
print(itr.next())      # returns 7
print(itr.next())      # returns -1
print(itr.next())      # returns 10
print(itr.hasNext())   # returns false
print(itr.next())