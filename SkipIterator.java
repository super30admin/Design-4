// Time complexity:O(n)
// space complexity:O(n)

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Iterator<Integer> it;
    Integer nextEl;
    public SkipIterator(Iterator<Integer> it) {
        map=new HashMap<>();
        this.it=it;
        nextEl=it.next();// nextEl = it.next(); //2
	}
	public boolean hasNext() {
       return nextEl==null;
	}
	public Integer next() {
       int temp=nextEl;
        advance();
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(Integer val){
        if(val==nextEl)
        {
            map.put(val,map.getOrDefault(val,0)-1);
            map.remove(val,0);
        }
        else
        {
             map.put(val,map.getOrDefault(val,0)+1);
            advance();
        }
	}
    // used for putting nextEl pointer of skip iterator at the next valid el in my native iterator
    private void advance(){ 
        nextEl=null;
        while(nextEl==null &&it.hasNext())
        {
            Integer el =it.next();
            if(map.containsKey(el))
            {
                 map.put(el,map.getOrDefault(el,0)-1);
                 map.remove(el,0);
            }
            else
            {
                nextEl=el;
            }
        }
      
    }
}

public class Main {

          public static void main(String[] args) {
            
          SkipIterator itr = new SkipIterator(Arrays.asList(2,2, 3, 5, 6, 5, 8, 5).iterator());
              
               
                System.out.println(itr.hasNext()); // true
                itr.skip(2); 
                itr.skip(2);
                itr.skip(3);
                System.out.println(itr.next()); // returns 5
                itr.skip(5);
                System.out.println(itr.next()); // returns 6
                System.out.println(itr.next());//retunr 8
                itr.skip(5);
                System.out.println(itr.hasNext());// false

              
                // itr.skip(5);
                // System.out.println(itr.next()); // returns 7
                // // itr.skip(5);
                // itr.skip(5);
                // System.out.println(itr.next()); // returns 7
                // System.out.println(itr.next()); // returns -1
                // System.out.println(itr.next()); // returns 10
                // System.out.println(itr.hasNext()); // false

      }

  }

