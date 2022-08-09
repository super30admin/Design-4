// "static void main" must be defined in a public class.


class SkipIterator implements Iterator<Integer> {
  
   public SkipIterator(Iterator<Integer> it) {
    
    }

   @Override
     public boolean hasNext() {
      
     }

   @Override
        
    // 5,6,7,5,6,8,9,5,5,6
     public Integer next() {
       
     }

   public void skip(int num) {
      
   }

   private void advance() {
       
       
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
