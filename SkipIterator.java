
//Time complexity - O(1) 
//Space Complexity - O(n) - number of elements to be skipped
/**
    store the skip values as key and number of times skip called for that number is saved in     hashmap.
    Maintain global next element.
    when the skip() is called 
    i) check the nextElement is same as the number to be skipped
    ii) if its true advance the iterarator
    iii) else add it in hashmap; 
    iv) once it is skipped reduce the count from hash map
    **/
    


class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    HashMap<Integer, Integer> map;
    Integer nextEl;


    public SkipIterator(Iterator<Integer> it) {
      this.it = it;
      map = new HashMap<>();
      advance();
    }

    @Override
    public boolean hasNext() {
      return nextEl != null;
    }

    @Override
    public Integer next() {
      Integer temp = nextEl;
      advance();
      return temp;
    }

    public void skip(int num) {
        if(num != nextEl) {
          if(!map.containsKey(num)) {
            map.put(num,map.getOrDefault(num,0) +1);
          }
        } else {
          advance();
        }
    }

    private void advance() {
        nextEl = null;
        while(nextEl == null && it.hasNext()) {
          int el = it.next();
          if(!map.containsKey(el)) {
            nextEl = el;
          } else {
            map.put(el,map.get(el)-1);
            map.remove(el,0);
          }
        }
    }
}

public class Main {
        public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
          System.out.println(it.next());
          System.out.println(it.next());
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.next());
    }
}