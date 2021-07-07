package Nov25;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//https://leetcode.com/discuss/interview-question/341818

class SkipIterator implements Iterator<Integer> {
 // map to identify which integer to skip(key) and how many times(value_)
 Map<Integer, Integer> skipMap;
 Iterator<Integer> it;
 Integer nextElement;

 public SkipIterator(Iterator<Integer> it) {
     skipMap = new HashMap<>();
     this.it = it;
     //place the nextElement pointer in skip iterator to first element in the given iterator
     //nextElement = it.next();
     advance();
 }

 @Override
 public boolean hasNext() {
     if (nextElement != null) {
         return true;
     }
     return false;
 }

 @Override
 public Integer next() {
    Integer temp = nextElement;
    //nextElement = it.next();
     advance();
    return temp;
 }

 public void skip(int num) {
     if (num == nextElement) {
 // dont put the num into hashmap. Just advance it to next valid position.
         advance();
     } 
     else {
         skipMap.put(num, skipMap.getOrDefault(num, 0) + 1);
     }
 }

 private void advance() {
     nextElement = null;
     while (nextElement == null && it.hasNext()) {
         Integer element = it.next();
         if (skipMap.containsKey(element)) {
             skipMap.put(element, skipMap.getOrDefault(element, 0) - 1);
             if (skipMap.get(element) == 0) {
                 skipMap.remove(element);
             }
         }  
         else {
             nextElement = element;
         }
     }
 }
}

public class Main {
     public static void main(String[] args) {
     SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
     System.out.println(it.hasNext());
     it.skip(2);
     it.skip(1);
     it.skip(3);
     System.out.println(it.hasNext());
 }
}