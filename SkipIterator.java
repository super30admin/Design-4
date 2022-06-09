//time o(1)
//space O(1)
// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        itr.hasNext(); // true
        itr.next(); // returns 2
        itr.skip(5);
        itr.next(); // returns 3
        itr.next(); // returns 6 because 5 should be skipped
        itr.next(); // returns 5
        itr.skip(5);
        itr.skip(5);
        itr.next(); // returns 7
        itr.next(); // returns -1
        itr.next(); // returns 10
        itr.hasNext(); // false
        itr.next(); // error
    }
}


class SkipIterator implements Iterator<Integer> {
    Integer nextEl;
    Iterator<Integer> it;
    HashMap<Integer, Integer> map;
	public SkipIterator(Iterator<Integer> it) {
        this.map = new HashMap<>();
        this.it = it;
        advance();
	}
    
    private void advance(){
        this.nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer el = it.next();
            if(!map.containsKey(el)){
                nextEl =el;
            }else{
                map.put(el, map.get(el)-1);
                map.remove(el,0);
            }
        }
    }

	public boolean hasNext() {
       return nextEl!=null;
	}

	public Integer next() {
          Integer result = nextEl;
        advance();
        return result;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val == nextEl){
            advance();
        }else{
            map.put(val,map.getOrDefault(val, 0)+1);
        }
	}
}

