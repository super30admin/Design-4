# Time Complexity: O(n) n - no of elements
# Space Complexity: O(n)
class SkipIterator:
    iteratorList = []
    current_p = 0
    skipList = []
    def __init__(self, arr):
        self.iteratorList = arr
    def hasNext(self):
        if self.current_p >= len(self.iteratorList):
            return False
        return True
    def next(self):
        if self.current_p >= len(self.iteratorList):
            print("No data left")
            return
        while self.iteratorList[self.current_p] in self.skipList:
            self.skipList.pop(0)
            self.current_p += 1

        ele = self.iteratorList[self.current_p]
        self.current_p += 1
        return ele

    def skip(self, no):
        self.skipList.append(no)

arr = [2, 3, 5, 6, 5, 7, 5, -1, 5, 10]
iterator = SkipIterator(arr)

print(iterator.hasNext())
print(iterator.next())
iterator.skip(5)
print(iterator.next())
print(iterator.next())
print(iterator.next())
iterator.skip(5)
iterator.skip(5)
print(iterator.next())
print(iterator.next())
print(iterator.next())
print(iterator.hasNext())
print(iterator.next())
