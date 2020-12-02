
'''
Not properly executed getting errors.
'''
class SkipIterator():
    nextEl = None

    def __init__(self, it):
        self.it = iter(it)
        self.map = {}
        self.advance()
        SkipIterator.nextEl = 0

    def has_next(self):
        return SkipIterator.nextEl != None

    def next(self):
        temp = SkipIterator.nextEl
        self.advance()  # set the new value to the nextEl
        return temp

    def skip(self, num):
        if SkipIterator.nextEl == num:
            self.advance()
        else:
            if num not in map:
                map[num] = 0
            map[num] += 1

    def advance(self):
        nextEl = None
        while not nextEl and next(self.it) is None:
            el = next(self.it)
            if el not in map:
                nextEl = el
            else:
                map[el] -= 1
                if map[el] == 0:
                    map.pop(el)


sol = SkipIterator([2,3,4,5,6,7,4])
print(sol.next())
print(sol.next())
