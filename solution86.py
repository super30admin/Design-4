#Time Complexity:O(1)
#Space Complexity:O(n)
class SkipIterator:
    def __init__(self, it):
        self.it = it;
        # create a hashmap to store the skip value , with key as skip value
        # and value as frequency of skip value
        self.h = defaultdict(int);
        # variable to indicate the current element
        self.count = 0
    def hashnext(self):
        return self.count < len(self.it);

    def next(self):

        # if the current element is not in skipping list, return the elt at current indes
        # and move the pointer to next elemnet 
        if(self.it[self.count] not in self.h):
            ans = self.it[self.count];
            self.count += 1;
            return ans;
        # iterate until the current element is present in skipping list
        while(self.count < len(self.it) and self.it[self.count] in self.h):
            self.h[self.it[self.count]] -= 1;
            if(self.h[self.it[self.count]] == 0):
                self.h.pop([self.it[self.count]]);
            self.count += 1;

        # if all the elements are processed in the iterator, return error
        if(self.count >= len(self.it)):
            return -1;

        ans = self.it[self.count];
        self.count += 1;
        return ans;
    def skip(self, val):
        self.h[val] += 1;
    '''