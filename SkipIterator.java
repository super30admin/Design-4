//time o(1)
//space O(1)
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        itr.hasNext(); 
        itr.next(); 
        itr.skip(5);
        itr.next(); 
        itr.next(); 
        itr.next(); 
        itr.skip(5);
        itr.skip(5);
        itr.next(); 
        itr.next(); 
        itr.next(); 
        itr.hasNext(); 
        itr.next(); 
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
	public void skip(int val) {
        if(val == nextEl){
            advance();
        }else{
            map.put(val,map.getOrDefault(val, 0)+1);
        }
	}
}