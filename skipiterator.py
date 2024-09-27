
Executed correctly in Pycharm IDE.

class SkipIterator:
    def __init__(self,iterator):
        self.iterator=iterator
        self.skipCount=dict()
        self.cursor=None
        self.seekCursor()
#checks whether there is an next element and returns false is it is the end of the iterator.0 (O(1))
    def hasnext(self):
        if self.cursor==None:
            return False
        return True
#returns the cursor element.--> O(1) or O(n) depends on the situation.
    def next(self):
        element=self.cursor
        self.seekCursor()
        return element
#stores the num and skips the first occurence of num 
    def skip(self,num):
        if self.cursor==num:
            self.seekCursor()
        else:
            if num not in self.skipCount:
                self.skipCount[num]=1
            else:
                self.skipCount[num]=self.skipCount[num]+1
#It makes the cursor reach a safe position by skipping the required elements.
    def seekCursor(self):
        self.cursor=None
        #running the loop till cursor is having a valid value it means that cursor should be made to skip the positions in the given hashmap and reach a safe position.
        while self.cursor==None :
            try:
                element = next(self.iterator)
                if element not in self.skipCount:
                    self.cursor=element
                else:
                    self.skipCount[element]=self.skipCount[element]-1
                    if self.skipCount[element]==0:
                        del self.skipCount[element]
            except:
                return

if __name__=="__main__":
    list1=[2,3,5,6,5,7,5,-1,5,10]
    iterator=iter(list1)
    skipiterator=SkipIterator(iterator)
    print(skipiterator.hasnext())
    print(skipiterator.next())
    skipiterator.skip(5)
    print(skipiterator.next())
    print(skipiterator.next())
    print(skipiterator.next())
    skipiterator.skip(5)
    skipiterator.skip(5)
    print(skipiterator.next())
    print(skipiterator.next())
    print(skipiterator.next())
    print(skipiterator.hasnext())

