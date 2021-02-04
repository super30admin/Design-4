//Problem 86 : Skip Iterator
//TC: O(1)
//SC: O(1)

/*Steps
Main Concept is advace(), that will be first called in the constructor while class object is instantiated. So that we can get the next element and move native iterator pointer. Then advance will be called in skip function also, because there could be a acse when element to be skipped and the nextElement both are same, at that time advance() will help in getting the valid nextElement and move the native iterator pointer. If skip element is not equal to the nextElement then just store the skip element in the map along with its frequency so that it can be skipped in future.

In addition call advance() in next() also to move nextElem pointer

*/

import java.util.*;

class SkipIterator implements Iterator<Integer> {


    private Iterator<Integer> it;
    private Integer nextElem;
    private Map<Integer,Integer> map;//Map is used for storing future skipable items and their frequency
	public SkipIterator(Iterator<Integer> it) {
      this.it = it;
      this.map = new HashMap<>();  
      advance();//for getting the nextElemnent moving the pointer  
	}

	public boolean hasNext() {

        return nextElem!=null;
	}

	public Integer next() {

        Integer result = nextElem;//so that while moving the next pointer using advance() below I cannot loose my current Val 
        advance();//moving next pointer
        return result;
	}

    //used for putting nextElement pointer of skip iterator art the next valid el in my native iterator  
    private void advance(){
        nextElem = null;
        while(nextElem==null && it.hasNext()){
            Integer el = it.next();
            if(!map.containsKey(el)){
                nextElem = el;
            }else{
                map.put(el,map.get(el)-1);
                map.remove(el,0);
            }
        }
        
    }


	/**

	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.

	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.

	*/ 

	public void skip(int val) {
        
        if(val==nextElem){
            advance();//There could be a case when skip element  and next element are same. Therefore move pointer to the valid element pointer;
        }else{
            //Future element record it inside the map
          map.put(val,map.getOrDefault(val,0)+1);     
        }
	}
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
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

        System.out.println(itr.next()); // error
        
    }

}