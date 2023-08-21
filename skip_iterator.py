class SkipIterator:
    def __init__(self, iterator) -> None:
        self.dict={}
        self.pointer=-1
        self.iterator=iterator

    def Next(self):
        if(self.hasNext()):
            self.pointer+=1
            if(self.dict[self.iterator[self.pointer]]>0):
                self.dict[self.iterator[self.pointer]]-=1
                self.pointer+=1
            return self.iterator[self.pointer]
        else:
            return None
    
    def skip(self, num):
        self.dict[num]+=1

    def hasNext(self):
        if(self.pointer+1>len(self.iterator)):
            return False
        return True
        pass
