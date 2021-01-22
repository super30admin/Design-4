/*
from collections import defaultdict 
from itertools import tee
class SkipIterator:
    def __init__(self, arr):
        self.it = iter(arr)
        self.map = defaultdict(int)
        self.i = None
    
    def skip(self, val):
        self.map[val] += 1
        
    def check(self,val):
        if val in self.map:
            self.map[val] -= 1
            
            if self.map[val] == 0:
                del self.map[val]
            return True
        return False
        
    def next(self):
        while self.hasNext():
            self.i = next(self.it, None)
            if not self.check(self.i):
                break
            
        return self.i
    
    def hasNext(self):
        temp, self.it = tee(self.it)
        if next(temp, None) == None:
            self.i = None
            return False
            
        return True
        

s = SkipIterator([2,5,2,1,0,3,5])
print(s.next())
print(s.hasNext())
s.skip(2)
s.skip(0)
s.skip(1)
print(s.next())
s.skip(5)
print(s.next())
print(s.next())
print(s.hasNext())
print(s.next())
print(s.hasNext())
print(s.next())
print(s.next())

*/

import java.util.*;

class SkipIterator {
    Iterator<Integer> it;
    HashMap<Integer, Integer> map;

    public SkipIterator(List<Integer> arr){
        it = arr.iterator();
        map = new HashMap();
    }
    public void skip(int val){
        map.put(val, map.getOrDefault(val, 0) + 1);
    }

    private boolean check(int val){
        if (map.containsKey(val)){
            map.put(val, map.get(val) - 1);

            if (map.get(val) == 0)
                map.remove(val);

            return true;
        }
        return false;
    }

    public boolean hasNext(){
        return it.hasNext();
    }

    public int next(){
        int i=-1;
        while (it.hasNext()){
            i = it.next();
            if (!check(i)){
                break;
            }
        }
        
        return i;
        
    }
}
public class Main{
    public static void main(String[] args){
        List<Integer> arr = new ArrayList<>();
        arr.add(2);
        arr.add(5);
        arr.add(2);
        arr.add(1);
        arr.add(0);
        arr.add(3);
        arr.add(5);
        
        SkipIterator it = new SkipIterator(arr);
            System.out.println(it.next());
            it.skip(2);
            it.skip(0);
            it.skip(1);
            System.out.println(it.next());
            System.out.println(it.next());
            System.out.println(it.next());
            System.out.println(it.hasNext());
            System.out.println(it.next());
    
    }
}