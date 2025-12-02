## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

# Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.

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
# Example:

# SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
# itr.hasNext(); // true
# itr.next(); // returns 2
# itr.skip(5);
# itr.next(); // returns 3
# itr.next(); // returns 6 because 5 should be skipped
# itr.next(); // returns 5
# itr.skip(5);
# itr.skip(5);
# itr.next(); // returns 7
# itr.next(); // returns -1
# itr.next(); // returns 10
# itr.hasNext(); // false
# itr.next(); // error

# Method1: Using hasNext() to do the heavy loading
# Time Complexity : skip() - O(1), next() - O(1), hasNext() - O(N)
# Space Complexity : O(K) -- K is the number of unique elements to be skipped
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No


# Your code here along with comments explaining your approach in three sentences only
# Here we are keeping a skip map to keep track to elements with their counts that needs to be skipped. 
# Here we are relying on the logic that hasNext() method is always be called just before the next() method.
# Whenever skip() is called we just increment the count of the element to be skipped in the skip map.
# When we call hasNext() we keep iterating through the native iterator until we find an element that is not in the skip map.
# If we find an element in the skip map we decrement its count and if the count reaches zero we remove it from the skip map and 
# shift the native iterator to the next element. A soon as we find the valid element that is not in the skip map, we update
# our nextEle and hasNext() returns true. If we exhaust the native iterator we return false. The next() method just returns
# the nextEle updated in hasNext(). Only constraint here is that hasNext() should be called just before next() always.
# Although in production hasNext() is always called before next().

class SkipIterator(object):
    def __init__(self, it):
        self.skipMap = {}
        self.nextEle = None
        # native iterator
        self.iterator = iter(it)

    # iterator function
    def hasNext(self):
        currEle = next(self.iterator, None)
        while(currEle):
            # check if it is to be skipped
            if(currEle in self.skipMap):
                # yes skip the current value
                self.skipMap[currEle] -= 1
                if(self.skipMap[currEle] == 0):
                    # remove it from the map
                    self.skipMap.pop(currEle)
                currEle = next(self.iterator, None)
            else:
                self.nextEle = currEle
                return True

        return False
    
    # iterator function
    def next(self):
        return self.nextEle
    
    # iterator function
    def __iter__(self):
        return self
    
    def skip(self, val):
        if(val not in self.skipMap):
            self.skipMap[val] = 0
        
        self.skipMap[val] += 1
        

# if(__name__ == "__main__"):
print("Method-1: Using hasNext() to do the heavy loading")
skipp = SkipIterator([5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8])
print(skipp.hasNext())
print(skipp.next())
skipp.skip(6)
skipp.skip(5)
skipp.skip(8)
skipp.skip(8)
skipp.skip(6)
skipp.skip(5)
skipp.skip(5)
print(skipp.hasNext())
print(skipp.next())
print(skipp.hasNext())
print(skipp.next())
print(skipp.hasNext())
print(skipp.next())
print(skipp.hasNext())


# Method2: Using skip() to do the heavy loading
# Time Complexity : skip() - O(N), next() - O(N), hasNext() - O(1)
# Space Complexity : O(K) -- K is the number of unique elements to be skipped
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No


# Your code here along with comments explaining your approach in three sentences only
# Here our skip() method and next() method does the heavy loading. In skip() method if the element to be skipped
# is the same as the nextEle we just move the native iterator to the next valid element as there is no time to add it to the skip
# map. If it is different we just add it to the skip map. In next() method we return the nextEle and move the native iterator
# to the next valid element by skipping all the elements that are in the skip map. In this implementation there is no 
# constraint that we have to necessarily call hasNext() before next() as skip() and next() itself takes care of moving 
# to the next valid element.

class SkipIterator(object):
    def __init__(self, it):
        self.skipMap = {}
        self.iterator = iter(it)
        self.nextEle = next(self.iterator, None)
        
    def hasNext(self):
        return (self.nextEle != None)
    
    def advance(self):
        currEle = next(self.iterator, None)
        while(currEle and (currEle in self.skipMap)):
            self.skipMap[currEle] -= 1
            if(self.skipMap[currEle] == 0):
                self.skipMap.pop(currEle)
            currEle = next(self.iterator, None)
            
        self.nextEle = currEle
        
        
        
    def skip(self, val):
        if(val == self.nextEle):
            # move the pointer of the native iterator, dont add to the map
            # find the next valid element -- important for hasNext() function
            self.advance()
        else:
            # just add the element to the skip map
            if(val not in self.skipMap):
                self.skipMap[val] = 0
                
            self.skipMap[val] += 1
        
    def next(self):
        temp = self.nextEle
        # check and move to the next valid element
        self.advance()
        return temp
        
        
print("Method-2: Using skip() to do the heavy loading")
# if(__name__ == "__main__"):
skipp = SkipIterator([5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8])
print(skipp.hasNext())
print(skipp.next())
skipp.skip(5)
print(skipp.next())
print(skipp.next())
skipp.skip(7)
skipp.skip(9)
print(skipp.next())
print(skipp.next())
print(skipp.next())
skipp.skip(8)
skipp.skip(5)
print(skipp.hasNext())
print(skipp.next())
print(skipp.hasNext())

