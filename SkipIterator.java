// TC : For each function is specified separately as comment
// SC  : is not required -- O(N)

// https://leetcode.com/playground/dkPLDXQr

class SkipIterator implements Iterator<Integer> {
    
    HashMap<Integer,Integer> map ;
    Iterator<Integer> it;
    Integer nextEle;
    public SkipIterator(Iterator<Integer> it){
        this.map = new HashMap<>();
        this.it = it;
        advance();
    }
  
    private void advance(){ //O(n)
       this.nextEle = null;
        while(it.hasNext()){
            Integer el = it.next();
            if(!map.containsKey(el)){
                nextEle = el;
                break;
            } else{
                map.put(el, map.get(el)-1);
                map.remove(el,0);
            }
        }
    }

     public void skip(int num) {  //O(n)
         if(nextEle == num){
             advance();
         } else{
             map.put(num, map.getOrDefault(num,0)+1);
         }
    }
   @Override
     public boolean hasNext() { //O(1)
        return nextEle != null;
     }

   @Override
     public Integer next() { //O(n)  
         Integer el = nextEle;
         advance();
         return el;
     }

  
}

public class Main {

         public static void main(String[] args) {

        SkipIterator sit = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8,6,5,2,1).iterator());

        System.out.println(sit.hasNext());// true
        System.out.println(sit.next()); //5   nextEl = 6
        sit.skip(5);  // will be store in map
        System.out.println(sit.next());// 6 nextEl = 7
        System.out.println(sit.next()); // 7 nextEl = 6
         sit.skip(7); // nextEl = 6
        sit.skip(9); // store in map
             
        System.out.println(sit.next()); // 6 nextEl = 8
             
         System.out.println(sit.next()); //8 
         System.out.println(sit.next());// 5
        sit.skip(8); //nextEl = null
        sit.skip(5);
        System.out.println(sit.hasNext()); //true 
        System.out.println(sit.next()); //6 
         System.out.println(sit.hasNext()); //false
         System.out.println(sit.next());// 5
         sit.skip(1);
         sit.skip(3);
         System.out.println(sit.hasNext()); //false 

     }

 }



