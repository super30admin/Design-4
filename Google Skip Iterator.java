class SkipIterator implements Iterator<Integer> {
    Integer nextElement;
    Iterator<Integer> it;
    HashMap<Integer ,Integer> hmap;
 
    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.hmap = new HashMap<>();
        helper();

    }

    @Override
    public boolean hasNext() {
        return nextElement != null ; // O(1)
    }

    
    public Integer next() {
        Integer curr = nextElement ;
        helper(); // get correct index/itertor for next time call
        return curr ;
    }

    public void skip(int num) {
       if(num == nextElement){
           helper();
       }else{
           //future skip element
           hmap.put(num , hmap.getOrDefault(num,0)+1);
       }
    }

    private void helper() {
      nextElement = null; // dont know which is my next so finding it
      while(nextElement == null && it.hasNext()){
          Integer element = it.next();// from original iterator
          if(hmap.containsKey(element)){
              hmap.put(element,hmap.get(element)-1);
              hmap.remove(element,0);
          }else{
              nextElement = element ;
          }
      }
}
}

public class Main {
        public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(2,3,5,6,5).iterator());
        System.out.println(it.hasNext());// true
        System.out.println(it.next());// retrun 2
        it.skip(5);
        System.out.println(it.next());// return 3
        System.out.println(it.next());// return 6
        System.out.println(it.hasNext());// true
      
    }
}