/**
The idea is to use hashmap to store the counter for number of skips and also override hasNext and next method which is critical. The advance helper function provides simple ways to provide skip and next functionality.
**/

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    Map<Integer,Integer> map;
    Integer nextElement;
	public SkipIterator(Iterator<Integer> it) {
        this.it  = it;
        this.map = new HashMap<>();
        advance();
        
	}

    
    @Override
	public boolean hasNext() {
        if(nextElement == null)
            return false;
        return true;
	}

    @Override
	public Integer next() {
        if(!hasNext()){
            System.out.println("Error");
            return -999999;
        }
        Integer curr = nextElement;
        advance();
        return curr;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(nextElement == val){
            advance();
        }
        else if(!map.containsKey(val)){
            map.put(val,1);            
        }else{
           map.put(val,map.get(val)+1);    
        }
	}
    
    private void advance(){
        nextElement = null;
        while(nextElement == null && it.hasNext()){
            Integer temp = it.next();
            if(!map.containsKey(temp)){
                    nextElement = temp;
            }else{
                map.put(temp,map.get(temp) - 1);
                if(map.get(temp) == 0)
                    map.remove(temp,0);
            }
        }
    }

}

class Main{
    public static void main(String[] args){
    SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
    System.out.println(itr.hasNext()); // true
    System.out.println(itr.next()); // returns 2
    itr.skip(5);
    System.out.println(itr.next()); // returns 3
    System.out.println(itr.next()); // returns 6 because 5 should be skipped
    itr.next(); // returns 5
    itr.skip(5);
    itr.skip(5);
    System.out.println(itr.next()); // returns 7
    System.out.println(itr.next()); // returns -1
    System.out.println(itr.next()); // returns 10
    System.out.println(itr.hasNext()); // false
    System.out.println(itr.next()); // error 
    
}    
}
