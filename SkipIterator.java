// https://leetcode.com/discuss/interview-question/341818

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Iterator<Integer> it;
    Integer nextElement;  // next element of skip iterator
    
    public SkipIterator(Iterator<Integer> it) {
       map = new HashMap();
       this.it = it;
       advance();
       //nextElement;
    }

    @Override
    public boolean hasNext() {
       return nextElement != null;
    }

    @Override
    public Integer next() {
       Integer result = this.it.next();
       advance();
       return result;
    }

    public void skip(int num) {
      if (num == nextElement)
         advance();
      else {
          map.put(num, map.getOrDefault(num, 0) + 1);
      }
    }

    private void advance() {
         nextElement = null;
         while (nextElement == null  && this.it.hasNext())
        {
            Integer currentElement = this.it.next();
            if (!map.containsKey(currentElement))
            {
                nextElement = currentElement;
            }
            else
            {
                 map.put(currentElement, map.get(currentElement) - 1 );
                 map.put(currentElement, 0 );
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
