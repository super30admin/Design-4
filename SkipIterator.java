/**
 * Time Complexity : O(n) -> mainly from `next()`
* Space Complexity : O(N)
* Did this code successfully run on Leetcode : Yes
*  Any problem you faced while coding this : No
 */


class SkipIterator implements Iterator<Integer> {
  Integer nextElement;
  HashMap<Integer, Integer> map;
  Iterator<Integer> it;
  public SkipIterator(Iterator<Integer> it) {
    this.map = new HashMap<>();
    this.it = it;

    makeValid(); 
  }

  public boolean hasNext() { // O(1)
    return nextElement != null;
  }

  public Integer next() { 
        Integer data = nextElement;
        makeValid(); // Update new nextElement

        return data; 
  }

  public void skip(int val) {
        if (val != nextElement) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        } else{
            makeValid();
        }
  }
    public void makeValid(){
        this.nextElement = null;

        while(it.hasNext() && nextElement == null){
            Integer temp = it.next();
            if(map.containsKey(temp)){
                map.put(temp, map.get(temp) - 1);
                map.remove(temp, 0);
            }else{
                nextElement = temp;
            }
        }
    }
  }
}
