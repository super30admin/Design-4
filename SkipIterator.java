// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
class SkipIterator implements Iterator<Integer> {

  Iterator<Integer> it; // Original iterator
  Map<Integer, Integer> count; // number to be skipped, no of times to be skipped
  Integer nextElement;
  public SkipIterator(Iterator<Integer> it) {
    this.it = it;
    count = new HashMap<>();
    advance();
  }

  void advance() {
    nextElement = null;
    while(nextElement == null && it.hasNext()) {
      Integer element = it.next();
      // Check if the next element according to the iterator is in the map.
      if(!count.containsKey(element)) {
        nextElement = element;
      }else {
        count.put(element, count.get(element)-1);
        count.remove(element, 0);
      }
    }
  }

  public boolean hasNext() {
    return nextElement!=null;
  }

  public Integer next() {
    if(!hasNext()) throw new RuntimeException("Empty");
    Integer element = nextElement;
    // Move to the skip iterator
    advance();
    return element;
  }


  /**
   * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
   * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
   */
  public void skip(int val) {
    if(!hasNext()) throw new RuntimeException("Empty");
    // If the nextElement is to be skipped, put it in the map and call advanced.
    if(nextElement == val) {
      count.put(val, count.getOrDefault(val,0)+1);
      advance();
    }else {
      count.put(val, count.getOrDefault(val,0)+1);
    }
  }
}