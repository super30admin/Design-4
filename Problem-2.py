class MyIterator:
    def __init__(self, nums):
        self.nums = nums
        self.index = 0

    def hasNext(self):
        return self.index < len(self.nums)

    def next(self):
        if not self.hasNext():
            raise StopIteration("No more elements.")
        value = self.nums[self.index]
        self.index += 1
        return value

class skipIterator:

    def __init__(self,iterator):
        self.nit = iterator
        self.skipMap = {}
        self.nextEl = None
        self.advance()

    def advance(self):
        while True:
            if(self.nit.hasNext()):
                el = self.nit.next()
                if el in self.skipMap and self.skipMap[el]>0:
                    self.skipMap[el] -= 1
                    if(self.skipMap == 0):
                        del self.skipMap[el]
                else:
                    self.nextEl = el
                    return
            else:
                self.nextEl = None
                return

    def hasNext(self):
        return self.nextEl is not None

    def next(self):
        temp = self.nextEl
        self.advance()
        return temp

    def skip(self,num):
        if(num == self.nextEl):
            self.advance()
        else:
            if num in self.skipMap:
                self.skipMap[num] +=1
            else:
                self.skipMap[num] = 1

if __name__ == "__main__":
    # Sample list of numbers to create an iterator from
    nums = [2, 3, 5, 6, 5, 7, 5, -1, 5, 10]
    iter_nums = MyIterator(nums)        # Create an instance of the iterator
    skip_iter = skipIterator(iter_nums)  # Create an instance of SkipIterator

    # Testing the SkipIterator
    print(skip_iter.hasNext())  # True
    print(skip_iter.next())      # Returns 2
    skip_iter.skip(5)            # Skip the number 5
    print(skip_iter.next())      # Returns 3
    print(skip_iter.next())      # Returns 6 because 5 should be skipped
    print(skip_iter.next())      # Returns 5
    skip_iter.skip(5)            # Skip the number 5
    skip_iter.skip(5)            # Skip the number 5 again
    print(skip_iter.next())      # Returns 7
    print(skip_iter.next())      # Returns -1
    print(skip_iter.next())      # Returns 10
    print(skip_iter.hasNext())   # False
