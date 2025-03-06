// Time Complexity :O(n) 
// Space Complexity :O(n) -> n is the number of skips for hashmap
// Did this code successfully run on Leetcode :yes
// Any problem you faced while coding this :no
 

// Your code here along with comments explaining your approach: here to implement SkipIterator i am using native iterator by putting native iterator on given list.


class SkipIterator implements Iterator<Integer>{
    Integer NextEl;
    HashMap<Integer,Integer> skipMap;
    Iterator<Integer> it;
    public SkipIterator(Iterator<Integer> it){
        this.it=it;
        this.skipMap=new HashMap<>();
        advance(); // to set skipIt nextEl by using  native itterator next
    }
    public void advance(){//O(n)
        this.NextEl =null;
        while(NextEl == null && it.hasNext()){
            int curr= it.next();
            if(!skipMap.containsKey(curr)){
                this.NextEl=curr; //assignment native next element to skipIterator NextEl
            }else{
                skipMap.put(curr,skipMap.get(curr)-1); //decrementing skip value
                skipMap.remove(curr,0); //it removes key if value becomes 0
            }
        }
        System.out.println("next"+NextEl);
        
    }
    public boolean hasNext(){ // O(1)
        return NextEl != null; //return true if its not null
        
    }
    public Integer next(){ //O(n)
        int temp =this.NextEl;
        advance();  
        return temp;
        
    }
    public void skip(int val){//O(n) 
        if(val == NextEl){
        advance(); 
        }else{
             skipMap.put(val,skipMap.getOrDefault(val,0)+1); 
        }
    }
}




// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SkipIterator st = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator()); //putting native iterator on input
        st.hasNext(); // true
        st.next(); // returns 2
        st.skip(5);
        st.next(); // returns 3
        st.next(); // returns 6 because 5 should be skipped
        st.next(); // returns 5
        st.skip(5);
        st.skip(5);
        st.next(); // returns 7
        st.next(); // returns -1
        st.next(); // returns 10
        st.hasNext(); // false
        st.next(); // error
            }
}