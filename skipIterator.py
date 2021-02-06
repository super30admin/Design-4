# class SkipIterator implements Iterator<Integer> {

# 	public SkipIterator(Iterator<Integer> it) {
# 	}

# 	public boolean hasNext() {
# 	}

# 	public Integer next() {
# 	}

# 	/**
# 	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
# 	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
# 	*/ 
# 	public void skip(int val) {
# 	}
# }
class SkipIterator:
    def __init__(self, it):
        self.iterator = iter(it)
        self.mapping = defaultdict()
        self.ptr = None
        
    def hasNext(self):
        try:
            while not self.ptr:
                nextVal = next(self.iterator) #error occurs on like 63 if theres no trycatch
                if self.mapping.get(nextVal, 0) > 0:
                    self.mapping[nextVal] -= 1
                else:
                    self.ptr = nextVal 
            return True
        except:
            return False
    def next(self):
        if not self.ptr:
            self.hasNext()
        temp = self.ptr
        self.ptr = None
        return temp
    
    def skip(self, val):
        if val not in self.mapping:
            self.mapping[val] = 1
        else:
            self.mapping[val] += 1
        return
    
            
itr = SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10])
print(itr.hasNext()) #// true
print(itr.next()) #// returns 2
print(itr.skip(5))
print(itr.next()) #// returns 3
print(itr.next()) #// returns 6 because 5 should be skipped
print(itr.next()) #// returns 5
print(itr.skip(5))
print(itr.skip(5))
print(itr.next()) #// returns 7
print(itr.next()) #// returns -1
print(itr.next()) #// returns 10
print(itr.hasNext()) #// false
print(itr.next()) #// error





        