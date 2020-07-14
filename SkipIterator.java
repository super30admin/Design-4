// Time Complexity: O(1) average; O(n) worst
// Space Complexity - O(n)

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Iterator<Integer> it;
    // nextEl of my skip Iterator
    Integer nextEl;
    public SkipIterator(Iterator<Integer> it) {
        map = new HashMap<>();
        this.it = it;
        advance();
	}
	public boolean hasNext() {
       return nextEl != null;
	}
	public Integer next() {
        int temp = nextEl;
        advance();// for putting the next pointer at valid next location
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(Integer val){
        if(val == nextEl){
            advance();// skip it right now present
        } else {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
	}
    // used for putting nextEl pointer of skip iterator at the next valid el in my native iterator
    private void advance(){
        // find new nextEl of skip iterator
        // first the previous nextEl can be declared 
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            int el = it.next(); // it moves the next pointer of my natinext location
            if(map.containsKey(el)){
                // reduce skip count
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            } else {
                nextEl = el;
            }
        }
    }
}

public class Main {

          public static void main(String[] args) {
            
          SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
              
               
                System.out.println(itr.hasNext()); // true
                System.out.println(itr.next()); // returns 2
                itr.skip(5);
                System.out.println(itr.next()); // returns 3
                System.out.println(itr.next()); // returns 6 because 5 should be skipped
                System.out.println(itr.next()); // returns 5
                itr.skip(5);
                itr.skip(5);
                System.out.println(itr.next()); // returns 7
                System.out.println(itr.next()); // returns -1
                System.out.println(itr.next()); // returns 10
                System.out.println(itr.hasNext()); // false

      }

  }

