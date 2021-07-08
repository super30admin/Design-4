import com.sun.javafx.image.IntPixelGetter;
import java.util.Arrays;
import java.util.HashMap;

// Time Complexity : O(n);
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach



import java.util.Iterator;

public class SkipIterator {

private HashMap<Integer,Integer> countMap;
private Iterator<Integer> iterator;
Integer nextElement;
public SkipIterator(Iterator<Integer> it ) {
  this.iterator =it;
  countMap = new HashMap<>();
   advance();
}

public boolean hasNext(){

  return nextElement!=null;
}

public Integer next(){
   int temp = nextElement;
    advance();
    return temp;
}
public void skip(int value){
  if(value == nextElement){
    advance();
  }
  else{
    countMap.put(value, countMap.getOrDefault(value,0)+1);
  }
}

  private  void advance() {
     nextElement = null;
     while(nextElement ==  null && iterator.hasNext()){
       Integer element  = iterator.next();
       if(countMap.containsKey(element)){
        countMap.put(element, countMap.get(element)-1 );
        countMap.remove(element,0);
       }else{
         nextElement = element;
       }

     }
  }

}




class Main {

  public static void main(String[] args) {

    SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());

    System.out.println(it.hasNext());

    it.skip(2);

    it.skip(1);

    it.skip(3);

    System.out.println(it.hasNext());

  }

}
