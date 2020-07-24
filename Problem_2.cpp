class SkipIterator implements Iterator<Integer> {

      Iterator<Integer> it;

      HashMap<Integer,Integer> map;

      Integer nextEl;

   public SkipIterator(Iterator<Integer> it) {
        this.it = it;

         map = new HashMap<>();

         advance();

     }

     public boolean hasNext() {

         return nextEl != null;

     }

   
     public Integer next() {

         int temp = nextEl;

         advance();

         return temp;

     }

   public void skip(int num) {

         if (nextEl == num) {

             advance();

         } else {

             map.put(num, count.getOrDefault(num, 0) + 1);

         }

     }

   private void advance() {

         nextEl = null;

         while (nextEl == null && it.hasNext()) {

             Integer el = it.next();

             if (!count.containsKey(el)) {

                 nextEl = el;

             } else {

                 count.put(el, count.get(el) - 1);

                 count.remove(el, 0);

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

