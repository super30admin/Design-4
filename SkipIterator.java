// space : O(n)
class SkipIterator implements Iterator<Integer> {
    Stack<Iterator> stack ;
    Iterator<Integer> nit;
    Integer nextEle;
    HashMap<Integer, Integer> skipMap;

public SkipIterator(Iterator<Integer> it) {

    this.stack = new Stack<>();
    this.nit = it;
    this.skipMap = new HashMap<>();
    setNextEle(); // set next element from null to valid ele
}

private void setNextEle(){    // O(n)
   
    nextEle = null; // reset
    
    while(nit.hasNext()&& nextEle ==null){
        Integer ele = nit.next(); // get nexte le
       
        if(skipMap.containsKey(ele)){ // if present in map
            skipMap.put(ele, skipMap.get(ele)-1); // skip and reduce count
            skipMap.remove(ele,0);  // remove if zero
        }
        else
        nextEle=ele; // assign it to ele
    }
    
}

public boolean hasNext() {   //  O(1)
    if(nextEle !=null)  
    return true;
return false;
}

public Integer next() {  // O(n)
    Integer result = nextEle;   // store in variable 
    setNextEle(); // set to nxt valid number
    return result; 
}

/**
* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
*/ 
public void skip(int val) {   //O(n)
    if(nextEle!=null && val == nextEle){   // curr elem and skip are same just set nextEle to valid num
        setNextEle();
    }
    else
        skipMap.put(val,skipMap.getOrDefault(val,0)+1);  // add to hashmap for future checking
}
}

public class Main{
public static void main(String[] args){
SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());

System.out.println(itr.hasNext()); // true
System.out.println(itr.next()); // returns 2
itr.skip(5);
System.out.println(itr.next()); // returns 3
System.out.println(itr.next());// returns 6 because 5 should be skipped
System.out.println(itr.next()); // returns 5
itr.skip(5);
itr.skip(5);
System.out.println(itr.next()); // returns 7
System.out.println(itr.next()); // returns -1
System.out.println(itr.next()); // returns 10
System.out.println(itr.hasNext()); // false
System.out.println(itr.next()); // null
}
}
