// "static void main" must be defined in a public class.


class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> itr;
    HashMap<Integer, Integer> skipMap;
    Integer nextEl
   public SkipIterator(Iterator<Integer> it) {
      this.itr = it;
      this.skipMap = new HashMap<>();
      advance();
    }

   @Override
     public boolean hasNext() {
       return nextEl != null;
     }

   @Override
        
    // 5,6,7,5,6,8,9,5,5,6
     public Integer next() {
        Integer temp = nextEl;
        advance();
        return temp;
     }

   public void skip(int num) {
      if (num == nextEl) {
        advance();
      } else {
        skipMap.put(num, map.getDefault(num, 0) + 1);
      }
   }

   private void advance() {
      nextEl = null;
      while(itr.hasNext()) {
        Integer el = itr.next();
        if (skipMap.containsKey(el)) {
          skipMap.put(num, skipMap.get(el) - 1)
          skipMap.remove(el, 0)
        } else {
          nextEl = el
        }
      }
       
   }

 }

public class Main {

         public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6).iterator());

         System.out.println(it.hasNext());
         it.skip(6);// 6 --nextEl
        it.skip(5);// 6
         System.out.println(it.next()); //7
         it.skip(5);
             System.out.println(it.next());// 6
             System.out.println(it.next()); // 8
          it.skip(5);
        it.skip(8);
             
        System.out.println(it.next());
             
         System.out.println(it.next());
         System.out.println(it.next());
         System.out.println(it.next());
         // it.skip(1);

//          it.skip(3);

         System.out.println(it.hasNext());

     }

 }