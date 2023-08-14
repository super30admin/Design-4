// "static void main" must be defined in a public class.


class SkipIterator implements Iterator<Integer> {
    //Iterator to use in all functions
    Iterator<Integer> nit;
    //To keep track of where the iterator is
    Integer nextEl;
    //To store the integeres that we have to skip
    HashMap<Integer, Integer> map;
   public SkipIterator(Iterator<Integer> it) { 
       this.nit = it;
       map = new HashMap<>();
    }
    
   @Override
    //Get the value from nextEl and check if that is null. Also check if we have to skip it or not
     public boolean hasNext() { //O(n)  
         Integer temp = null;
        if(nextEl != null){
            return true;
        } else{
            if(nit.hasNext()){
                temp = nit.next();
                while( map.containsKey(temp)){
                    
                    int cnt = map.get(temp);
                    if(cnt == 1){
                        map.remove(temp);
                    }
                    else{
                        map.put(nextEl, cnt-1);
                    }
                    temp = null;
                    if(nit.hasNext()) temp = nit.next();
                }
                
            }
        }
         nextEl = temp;
         return nextEl != null;
     }

   @Override
     public Integer next() { //O(n)
        if(nextEl == null){
            this.hasNext();
            
        }
         int temp = nextEl;
         nextEl = null;
         this.hasNext();
         return temp;
     }

    public void skip(int num) { //O(1)
        
        if(nextEl != null && nextEl == num){
            nextEl = null;
        }
        else{
            map.put(num, map.getOrDefault(num,0)+1);
        }
    }
}

public class Main {

         public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

         System.out.println(it.hasNext());// true
        it.skip(5);// 
         System.out.println(it.next()); //6
         it.skip(5);
             System.out.println(it.next());// 7
             System.out.println(it.next()); // 6
          it.skip(8); // 
        it.skip(9); //
             
        System.out.println(it.next()); // 5
             
         System.out.println(it.next()); //5
         System.out.println(it.next());//6 
             System.out.println(it.hasNext());// true
         System.out.println(it.next());// 8
         // it.skip(1);

//          it.skip(3);

         System.out.println(it.hasNext()); //false 

     }

 }