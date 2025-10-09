# tc : next, processnext, skip:  armotixed O(1), hasNext O(1)
# sc : O(k) k being the number of the skip elements 
# code ran successfuly for the test driver 
class SkipIterator:
    # lets maintin a map where we store the valu of the skip elements and also have a nextelement so that we dont loose its acess when parsing next of the iter and a nextiter to process first itertion and once done we can move to the next iteration 
    def __init__(self, it):
        self.skipelements = {}
        self.nextEl = None 
        self.nextiter = iter(it)
        self.processnext() # start the iteration 


    def hasNext(self):
        # if we have the next element then return true 
        return self.nextEl is not None

    def next(self):
        # we should retun the next element
        element = self.nextEl

        # now the nextel pointer should move to the next in the iter 
        self.processnext()

        return element
       
    def processnext(self): # this function is to move oru nextelement properly such that we skip the skip elements and equate our nextEl correclty
        self.nextEl = None # resettign the nextel to none 

        while self.nextEl is None: # once we foudn the nextele we can stop the while loop 
           try:
                element = next(self.nextiter) # get the next elemtn from the iterator
                if element in self.skipelements: # if we have to skip this we will skip it but before that we have to reduce the count of the skip element 
                    self.skipelements[element] -= 1
                    if self.skipelements[element] == 0: # if after reducing it count qual to 0 then we remove from the map
                        del self.skipelements[element]
                else : 
                    self.nextEl = element # if not we got our nextele
           except StopIteration: # if the iter is exhausted then stop 
               break

    def skip(self, val):
        # if our next element we should skip then we have to process in the iter thus skipping the val
        if val == self.nextEl:
            self.processnext()
        else : # if we havent coem across this element yet and this value comes in future we need to skip at that time
            self.skipelements[val] = self.skipelements.get(val, 0) + 1 # if we have to skip this more than once then increment the count accordingly 
        


# Test driver
itr = SkipIterator([5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8])
print(itr.hasNext())  # True
itr.skip(5)
print(itr.next())     # 6
itr.skip(5)
print(itr.next())     # 7
print(itr.next())     # 6
itr.skip(8)
itr.skip(9)
print(itr.next())     # 5
print(itr.next())     # 5
print(itr.next())     # 6
print(itr.hasNext())  # True
print(itr.next())     # 8
print(itr.hasNext())  # False