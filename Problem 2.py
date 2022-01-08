 class SkipIterator(Iterator):
    # Space: O(n)
    def __init__(self, iterator):
        self.iterator = iterator
        self.next = None
        self.skip = {}
    def skip(self, number):
        if number in self.skip:
            self.skip[number] += 1
        else:
            self.skip[number] = 0
    # Time: O(n)
    # Space: O(1)
    def has_next(self):
        if self.next != None:
            return True
        while self.iterator.has_next():
            temp = self.iterator.next()
            if num not in self.skip or self.skip[num] == 0:
                self.next = temp
                return True
            else:
                self.skip[num] -= 1
        return False
    # Time: O(1)
    # Space: O(1)
    def next(self):
        if self.has_next() == False:
            raise Exception('There is no next')
        else:
            nxt = self.next
            self.next = None
            return nxt
    
