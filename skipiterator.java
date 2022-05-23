// learn how to write classes /methods in design object oriented 

class SkipIterator implements Iterator<Integer> {
   private final Iterator<Integer> it;
   private Integer nextEl; 
   private HashMap<Integer, Integer> skipCount;
     
   public SkipIterator(Iterator<Integer> it) {
       this.it =it;
       this.skipCount = new HashMap<>();
       this.nextEl = null;
       advance();
        
       


     }

   @Override

     public boolean hasNext() {
         return nextEl != null; 

         

     }

   @Override

     public Integer next() {
         int temp = nextEl;
         advance();
         return temp;
         

         

     }

   public void skip(int num) {
        if(num == nextEl){
            advance();
        }else
        {
        skipCount.put(num, skipCount.getOrDefault(num, 0) +1);
        }
        
       
       
        

         

     }

   private void advance() {
       
    nextEl = null;
    
        while(nextEl == null && it.hasNext()){
                
            
             Integer el = it.next();

             if (!skipCount.containsKey(el)) {

                 nextEl = el;

             } else {

                 skipCount.put(el, skipCount.get(el) - 1);

                 //skipCount.remove(el, 0);

             }

            
    
        
    }
    

         

     }// end advAnce

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
