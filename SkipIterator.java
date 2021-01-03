/*
Approach:

Most important part here is to retain the infromation regarding the elements to be skipped. 
Because count of the elements that needs to be skipped can be more than 1. 
So here we will use a hashmap. What hashmap will contain is which element is supposed to be skipped and how many time it should be skipped.

We will also a pointer to current element that we are supposed to return.

next(): Whenever it is called we will return the value of our current pointer. 
Also at the same time we need to advance ahead untill current pointer skips over all the elements that needs to skipped and at last points the current element.

hasNext(): it just returns if the pointer to current element is null or not.

skip(): it just adds element in the hashmap. And if the key is already present than update its skip frequency.

Time complexity: O(k); where k = no of elements to be skipped
Space complexity: O(n); where n = no of elements in given array
*/

import java.util.HashMap;
import java. util. Iterator;
import java. util. Arrays;

class SkipIterator implements Iterator<Integer> {

  Integer nextElement;
  HashMap<Integer, Integer> count;
  Iterator<Integer> it;

  public SkipIterator(Iterator<Integer> it) {
    this.it = it;
    this.count = new HashMap<>();
    advance();
  }

  @Override
  public boolean hasNext() {
    return nextElement != null;
  }

  @Override
  public Integer next() {
    if (!hasNext()) throw new RuntimeException("Next(): List is empty!");
    Integer val = nextElement;
    advance();
    return val;
  }

  /**
  * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
  * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
  */ 
  public void skip(int val) {
    if (!hasNext()) {
      throw new RuntimeException("hasNext(): List is empty!");
    }

    if (nextElement == val){
      advance();
    }
    if (!count.containsKey(val)){
      count.put(val, 1);
    }
    else{
      int c = count.get(val);
      count.put(val, c + 1);
    }

  }

  private void advance(){
    nextElement = null;
    while (nextElement == null && it.hasNext()){
      Integer el = it.next();
      if (!count.containsKey(el)){
          nextElement = el;
      }
      else{
          Integer c = count.get(el);
          count.put(el, c - 1);
          if (count.get(el) == 0){
            count.remove(el);
          }
      }
    }
  }

  public static void main(String[] args) {
    SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
    System.out.println(itr.hasNext()); // true
    System.out.println(itr.next()); // returns 2
    itr.skip(5);
    System.out.println(itr.next()); // returns 3
    System.out.println(itr.next()); // returns 6 because 5 should be skipped
    System.out.println(itr.next()); // returns 5
    itr.skip(5);
    itr.skip(5);
    System.out.println(itr.next()); // returns 7
    System.out.println(itr.next()); // returns -1
    System.out.println(itr.next()); // returns 10
    System.out.println(itr.hasNext()); // false
    System.out.println(itr.next()); // error
  }
}


