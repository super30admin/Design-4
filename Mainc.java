import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//Time Complexity : O(1)
//Space Complexity : O(n) for map of skip values
//Did this code successfully run on Leetcode : Yes
//Any problem you faced while coding this : No

// "static void main" must be defined in a public class.
public class Mainc {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SkipIterator itr = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6,8).iterator());
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
        itr.skip(5);
        System.out.println(itr.next());
        System.out.println(itr.next());
        itr.skip(7);
        itr.skip(9);
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next());
        itr.skip(8);
        itr.skip(5);
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        //System.out.println(itr.next());
    }
}

class SkipIterator implements Iterator<Integer> {
    Map<Integer,Integer> map;
    Integer nextel;
    Iterator<Integer> it;
	public SkipIterator(Iterator<Integer> it) {
        map=new HashMap<>();
        this.it=it;
        advance();
	}

	public boolean hasNext() {
        if(nextel!=null){
            return true;
        }
        return false;
	}

	public Integer next() {
        int tmp=nextel;
        advance();
        return tmp;
	}
    
    private void advance(){
        this.nextel=null;
        while(nextel==null && it.hasNext() ){
            Integer currel=it.next();
            if(!map.containsKey(currel)){
                nextel=currel;
            }else{
                int v=map.get(currel);
                v--;
                if(v==0){
                    map.remove(currel);
                }else{
                    map.put(currel,v);
                }
            }
        }
    }

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(nextel==val){
            advance();
        }else{
            map.put(val,map.getOrDefault(val,0)+1);
        }
	}
}