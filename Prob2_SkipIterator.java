// Time Complexity : Next() -> O(N), hasNext() -> O(1), skip() -> O(N) ==> Total = O(N)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach



class SkipIterator implements Iterator<Integer> {
    Integer nextElement;
    HashMap<Integer, Integer> map; //Any Integer K and how many times we will skip that K
    Iterator<Integer> it;
    public SkipIterator(Iterator<Integer> it) {
	    this.map = new HashMap<>();
        this.it = it;
        
        makeValid(); // Traverese through iterator
    }

	public boolean hasNext() { // O(1)
        return nextElement != null;
	}

	public Integer next() { //O(N)
        Integer data = nextElement;
        makeValid(); // Update new nextElement
        
        return data; 
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) { //When called, if value t skip is same as current nextElement then, update the hashmap with the count of that number
        if(val != nextElement){
            map.put(val, map.getOrDefault(val, 0) + 1);
        }else{
            makeValid(); //OTHERWISE set the nextElement
        }
	}
    public void makeValid(){ // Set the nextValid value for nextElement variable for future
        this.nextElement = null;
        
        while(it.hasNext() && nextElement == null){
            Integer temp = it.next();
            if(map.containsKey(temp)){
                map.put(temp, map.get(temp) - 1);
                map.remove(temp, 0);
            }else{
                nextElement = temp;
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        SkipIterator s = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6).iterator());
        
        System.out.println(s.hasNext());
        
        s.skip(6); s.skip(5);
        System.out.println(s.next());
        s.skip(5);
        
        System.out.println(s.next());
        System.out.println(s.next());
        s.skip(5);
        s.skip(8);
        }
}

